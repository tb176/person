/**  
 * Project Name : domain  
 * File Name	: HTTPNotification.java  
 * Package Name : com.gamaxpay.domain.model.snc  
 * Create Date  : 2012-11-20下午6:23:27  
 *
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.thirdapi.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.log4j.Logger;

/**
 * <p>HTTP协议通知</p>
 * 
 * <p>HTTP协议通知</p>
 * 
 ******************************************************** 
 * Date				Author		Changes <br/>
 * 2012-11-20下午6:23:27	Eric Cao	创建
 ******************************************************** 
 */
@Entity(name="HTTPNotification")
@DiscriminatorValue("H")
public class HttpNotification extends Notification
{
	
	/**
	 * mo9的主机名列表.
	 */
	private static final String[] mo9Host={"mo9.com","mo9.com.cn"};
    /**  
     * serialVersionUID:TODO
     */
    private static final long serialVersionUID = -2715760441341509956L;
    
    /**http协议待通知的的目标URL地址*/
    private String notifyUrl;
    /**http协议待通知的的目标方法*/
    private String notifyMethod;
    /**采用Get方法发起HTTP请求通知.*/
    public static final String NOTIFY_METHOD_GET = "GET";
    /**采用POST方法发起HTTP请求通知.*/
    public static final String NOTIFY_METHOD_POST="POST";
    
    @Column(name="http_url")
    public String getNotifyUrl()
    {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl)
    {
        this.notifyUrl = notifyUrl;
    }
    @Column(name="http_method")
    public String getNotifyMethod()
    {
        return notifyMethod;
    }
    public void setNotifyMethod(String notifyMethod)
    {
        this.notifyMethod = notifyMethod;
    }
    
    /* (non-Javadoc)
     * @see com.gamaxpay.domain.model.snc.Notification#calculateNextNotifyTime()
     */
    @Override
    public void calculateNextNotifyTime() {
    
    	if(notify2Mo9())
    	{/**如果通知到mo9的内部地址，则每隔两分钟重复通知一次.*/
    		 Calendar nextNotifyTime = Calendar.getInstance();
    		 nextNotifyTime.setTime(getLastNotifyTime());
    	     nextNotifyTime.add(Calendar.MINUTE, 2);
    	     setNextNotifyTime(nextNotifyTime.getTime());
    	     if(this.getNextNotifyTime().compareTo(getExpiredTime())>0)
    	     {/**下一次执行时间，已经超过了消息的有效时间，则将该消息取消*/
    	     	this.setStatus(STATUS_UNDO);
    	     }
    	}
    	else
    	{/**非mo9的目标通知地址，还是按照斐波那契数列重复回调.*/
	    	super.calculateNextNotifyTime();
    	}
    }
    
    /**
     * 
     * 当前消息是否是通知到mo9的主机.
     * @return
     * @return: boolean
     */
    private boolean notify2Mo9()
    {
    	try {
			String host = new URL(notifyUrl).getHost();
			for(String arg:mo9Host)
			{
				if(host.endsWith(arg))
				{
					return true;
				}
			}
		} catch (MalformedURLException e) {
			  Logger.getLogger(HttpNotification.class).warn("主机名识别错误.",e);
		}
    	return false;
    }
    
    /**  
     * TODO 
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
          
        // TODO Auto-generated method stub  
        return "HttpNotification{id:"+getId()+",url:"+notifyUrl+",message:"+getMessage()+"}";
    }
    
    public static void main(String[] args)
    {
    	HttpNotification m = new HttpNotification();
    	m.setNotifyUrl("https://localhost/abc.do");
    	System.out.println(m.notify2Mo9());
    	m.setNotifyUrl("https://127.0.0.1/abc.do");
    	System.out.println(m.notify2Mo9());
    	m.setNotifyUrl("https://sandbox.mo9.com.cn/abc");
    	System.out.println(m.notify2Mo9());
    	m.setNotifyUrl("https://www.mo9.com.cn/abcdef");
    	System.out.println(m.notify2Mo9());
    	m.setNotifyUrl("https://www.mo9.com/adf.do");
    	System.out.println(m.notify2Mo9());
    	m.setNotifyUrl("https://www.baidu.com/abcd");
    	System.out.println(m.notify2Mo9());
    }
}
