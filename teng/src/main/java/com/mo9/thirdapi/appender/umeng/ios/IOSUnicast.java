package com.mo9.thirdapi.appender.umeng.ios;


import com.mo9.thirdapi.appender.umeng.IOSNotification;

/**
 * 单播(unicast): 向指定的设备发送消息，包括向单个device_token或者单个alias发消息。
 * @author beiteng
 *
 */
public class IOSUnicast extends IOSNotification {
	public IOSUnicast(String appkey,String appMasterSecret) throws Exception{
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
