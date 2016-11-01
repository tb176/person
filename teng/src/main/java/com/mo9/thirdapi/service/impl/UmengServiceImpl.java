package com.mo9.thirdapi.service.impl;

import com.mo9.thirdapi.appender.umeng.UmengNotificationAppender;
import com.mo9.thirdapi.appender.umeng.UmengPushIosClient;
import com.mo9.thirdapi.domain.UmengPushNotification;
import com.mo9.thirdapi.domain.UmengResponeResult;
import com.mo9.thirdapi.service.UmengService;
import org.apache.log4j.Logger;


public class UmengServiceImpl implements UmengService {
	private Logger logger = Logger.getLogger(UmengServiceImpl.class);
	private UmengPushIosClient snc_umeng_ios_service = UmengPushIosClient.instance() ;
	UmengNotificationAppender appender = new UmengNotificationAppender();
	@Override
	public UmengResponeResult sendIOSBroadcast(String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSBroadcast(alert);
	}
	@Override
	public UmengResponeResult sendIOSUnicast(String deviceTokens, String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSUnicast(deviceTokens, alert);
	}
	@Override
	public UmengResponeResult sendIOSListcast(String deviceTokens, String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSListcast(deviceTokens, alert);
	}
	@Override
	public UmengResponeResult sendIOSGroupcast(String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSGroupcast(alert);
	}
	@Override
	public UmengResponeResult sendIOSFilecast(String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSFilecast(alert);
	}
	@Override
	public UmengResponeResult sendIOSCustomizedcast(String aliasType,String alias, String alert) throws Exception {
		return snc_umeng_ios_service.sendIOSCustomizedcast(aliasType, alias, alert);
	}
	@Override
	public UmengResponeResult pushUmeng(UmengPushNotification message)
			throws Exception {
		
		return appender.doPush(message);
	}
	
	
	
}
