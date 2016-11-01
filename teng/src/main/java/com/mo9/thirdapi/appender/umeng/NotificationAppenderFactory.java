/**  
 * Project Name : SystemNotificationCenter  
 * File Name	: NotificationAppenderFactory.java  
 * Package Name : com.mo9.suc.core.appender  
 * Create Date  : 2012-11-21上午11:06:17  
 *
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.thirdapi.appender.umeng;

import java.util.ArrayList;
import java.util.List;

import com.mo9.thirdapi.domain.Notification;
import org.apache.log4j.Logger;


/**
 * <p>NotificationAppender工厂</p>
 * 
 * <p>NotificationAppender工厂,同于统一管理各消息的appender处理.</p>
 * 
 ******************************************************** 
 * Date				Author		Changes <br/>
 * 2012-11-21上午11:06:17	Eric Cao	创建
 ******************************************************** 
 */
public class NotificationAppenderFactory
{
    /**工厂类，唯一实例*/
    private static NotificationAppenderFactory instance = null;
    
    /**Appender列表*/
    private List<INotificationAppender> appenders = null;
    
    private Logger log  = Logger.getLogger(NotificationAppenderFactory.class);
    
    /**
     * 
     * loadAppender:根据消息类型加载对应的Appender. 
     *  如果存在多个appender都支持同一个消息，则返回第一个appender.
     * @param message
     * @return
     */
    public INotificationAppender loadAppender(Notification message)
    {
        //参数验证
        for(INotificationAppender app:appenders)
        {
            if(app.support(message))
            {
                return app;
            }
        }
        /**如果无法找到合适的通知适配器，则抛出异常.*/
        log.error("无法找到合适的NotificationAppender,Message :"+message.toString());
        throw new IllegalArgumentException("无法找到合适的NotificationAppender,Message :"+message.toString());
    }
    
    /**
     * 
     * init:初始化工厂. <br/>  
     * TODO(DESC).<br/>  
     *
     */
    private static void init()
    {
        instance =  new NotificationAppenderFactory();
        instance.appenders = new ArrayList<INotificationAppender>();
        //Http消息处理
      /*  instance.appenders.add(new HttpNotificationAppender());
        //Email消息处理
        instance.appenders.add(new EmailNotificationAppender());
        //Sms消息处理
        instance.appenders.add(new SmsNotificationAppender());
        //语音验证码处理
        instance.appenders.add(new VoicePinNotificationAppender());
        //银行卡绑定消息
        instance.appenders.add(new BankBindNotificationAppender());
        //客户端推送
        instance.appenders.add(new JPushNotificationAppender());
        //19pay流量包
        instance.appenders.add(new Pay19FlowDataNotificationAppender());
        //weixin
        instance.appenders.add(new WeixinPushNotificationAppender());*/
        //umeng push
        instance.appenders.add(new UmengNotificationAppender());
    }
    /**
     * 
     * instance:(这里用一句话描述这个方法的作用). <br/>  
     * TODO(DESC).<br/>  
     *  
     * @return
     */
    public synchronized static  NotificationAppenderFactory instance()
    {
        if(instance==null)
        {
            init();
        }
        return instance;
    }
}
