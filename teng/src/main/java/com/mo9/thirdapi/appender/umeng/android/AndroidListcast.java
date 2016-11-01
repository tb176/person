package com.mo9.thirdapi.appender.umeng.android;

import com.mo9.thirdapi.appender.umeng.AndroidNotification;

/**
 * 列播(listcast): 向指定的一批设备发送消息，包括向多个device_token或者多个alias发消息。
 * @author beiteng
 *
 */
public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "listcast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}