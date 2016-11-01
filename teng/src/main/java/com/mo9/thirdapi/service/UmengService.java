package com.mo9.thirdapi.service;


import com.mo9.thirdapi.domain.UmengPushNotification;
import com.mo9.thirdapi.domain.UmengResponeResult;

public interface UmengService {
	//ios
	public UmengResponeResult sendIOSBroadcast(String alert)throws Exception;
	public UmengResponeResult sendIOSUnicast(String deviceTokens, String alert)throws Exception;
	public UmengResponeResult sendIOSListcast(String deviceTokens, String alert)throws Exception;
	public UmengResponeResult sendIOSGroupcast(String alert)throws Exception;
	public UmengResponeResult sendIOSFilecast(String alert)throws Exception;
	public UmengResponeResult sendIOSCustomizedcast(String aliasType, String alias, String alert)throws Exception;
	public UmengResponeResult  pushUmeng(UmengPushNotification message)throws Exception;
	
}
