package com.mo9.thirdapi.appender.umeng.android;

import com.mo9.thirdapi.appender.umeng.AndroidNotification;

/**
 * 广播(broadcast): 向安装该App的所有设备发送消息
 *  @author beiteng
 */


public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
