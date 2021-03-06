package com.mo9.thirdapi.appender.umeng.ios;

import com.mo9.thirdapi.appender.umeng.IOSNotification;

//文件播(filecast): 开发者将批量的device_token或者alias存放到文件, 通过文件ID进行消息发送。
public class IOSFilecast extends IOSNotification {
	public IOSFilecast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
