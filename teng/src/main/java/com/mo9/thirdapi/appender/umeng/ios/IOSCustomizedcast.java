package com.mo9.thirdapi.appender.umeng.ios;

import com.mo9.thirdapi.appender.umeng.IOSNotification;

/**
 *  @author beiteng
 * 自定义播(customizedcast): 开发者通过自有的alias进行推送, 可以针对单个或者一批alias进行推送，也可以将alias存放到文件进行发送。
 */

public class IOSCustomizedcast extends IOSNotification {
	public IOSCustomizedcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "customizedcast");	
	}
	
	public void setAlias(String alias,String aliasType) throws Exception {
    	setPredefinedKeyValue("alias", alias);
    	setPredefinedKeyValue("alias_type", aliasType);
    }
		
	public void setFileId(String fileId, String aliasType) throws Exception {
		setPredefinedKeyValue("file_id", fileId);
		setPredefinedKeyValue("alias_type", aliasType);
	}

}
