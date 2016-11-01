package com.mo9.thirdapi.appender.umeng.ios;

import com.mo9.thirdapi.appender.umeng.IOSNotification;

//广播(broadcast): 向安装该App的所有设备发送消息
public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
		
	}
}
