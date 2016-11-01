/**  
 * Project Name : SystemNotificationCenter  
 * File Name	: INotificationAppender.java  
 * Package Name : com.mo9.suc.core  
 * Create Date  : 2012-11-21上午10:40:38  
 *
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.thirdapi.appender.umeng;


import com.mo9.thirdapi.domain.Notification;

/**
 * <p>通知适配器</p>
 * 
 * <p>通知适配器,用户完成多种不同逻辑的消息通知处理.</p>
 * 
 ******************************************************** 
 * Date				Author		Changes <br/>
 * 2012-11-21上午10:40:38	Eric Cao	创建
 ******************************************************** 
 */
public interface INotificationAppender
{
    /**
     * 
     * support:判断当前适配能是否能够处理处理特定的通知消息. <br/>  
     * TODO(DESC).<br/>  
     *  
     * @param message 带验证的消息
     * @return 返回是否支持
     */
    public boolean support(Notification message);
    
    /**
     * 
     * notify:对特定的消息执行实际的通知操作 <br/>  
     * TODO(DESC).<br/>  
     *  
     * @param message 待执行的通知消息.
     */
    public void notify(Notification message);
}
