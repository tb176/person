/**
 * Project Name : SystemNotificationCenter
 * File Name	: HTTPNotificationAppender.java
 * Package Name : com.mo9.suc.core.appender
 * Create Date  : 2012-11-21上午11:02:23
 *
 * Copyright ©2011 moKredit Inc. All Rights Reserved
 */
package com.mo9.thirdapi.appender.umeng;

import com.mo9.thirdapi.domain.Notification;
import com.mo9.thirdapi.domain.UmengPushNotification;
import com.mo9.thirdapi.domain.UmengResponeData;
import com.mo9.thirdapi.domain.UmengResponeResult;
import com.mo9.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * 友盟消息推送
 *
 * @author lbqi/2016-4-12 17:55
 *
 */
public class UmengNotificationAppender implements INotificationAppender {
	private Logger log = Logger.getLogger(UmengNotificationAppender.class);

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 *
	 */
	@Override
	public boolean support(Notification message) {

		// TODO Auto-generated method stub
		return message instanceof UmengPushNotification;
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 *
	 */
	@Override
	public void notify(Notification message) {//
		UmengPushNotification uNotify = (UmengPushNotification) message;

		try {
			doPush(message);
			log.info("友盟请求的参数列表为：" + JsonUtil.toJson(uNotify));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	/**
	 *推送信息
	 * @param ticker
	 * @param text
	 * @param alias
	 */
	public void pushNotify(String ticker,String title,String text,String alias,String attacch){
		UmengPushNotification uPushVo = new UmengPushNotification();
		try{
			//自定义推送方式，以tag为关键字
			uPushVo.setType(UmengPushNotification.PushType.customizedcast.toString());
			uPushVo.setTicker(ticker);//消息到答时提示信息
			uPushVo.setTitle(title);
			//滑动通知栏看到的内容
			uPushVo.setText(text);
			//uPushVo.setDeviceTokens("AtEekah2uIYvJVeA2Xjyr6Ujqju2I2NP9WpXiJd6k4Cg");
			//推送时把创建时间置为空，不然会报错，主要是因为日期转换成json，会把年月日时分秒单独作为一个属性
			uPushVo.setCreateTime(null);
			//tag标签ios,android在友盟服务器上已绑定
			uPushVo.setAlias(alias);
			uPushVo.setAliasType("mobile");
			uPushVo.setAttach(attacch);
			UmengNotificationAppender appender = (UmengNotificationAppender) NotificationAppenderFactory.instance()
					.loadAppender(uPushVo);
			// 推送消息
			appender.doPush(uPushVo);
		}catch(Exception ee){
			log.warn("推送信息："+JsonUtil.obj2Json(uPushVo, new String[] { "user","group"})+" \n错误信息："+ee.getMessage());
		}
	}
	/**
	 * 推送方法
	 *
	 * @param message
	 * @return
	 */
	public UmengResponeResult doPush(Notification message) {//
		String platform = "";//判断走的是android还是ios
		UmengPushNotification uNotify = (UmengPushNotification) message;
		com.mo9.thirdapi.domain.UmengResponeResult respResult = null;
		try {
			// 如果platForm为空，则说明ios，android各推一次
			log.info("友盟请求的参数列表为：" + JsonUtil.toJson(uNotify));
			if (StringUtils.isBlank(uNotify.getPlatForm())
					|| UmengPushNotification.PlatFormType.ios.toString().equals(uNotify.getPlatForm())) {
				UmengPushIosClient uPushClient = new UmengPushIosClient(uNotify.getDeviceTokens(),
						uNotify.getMessage(), uNotify.getAttach());
				platform = "ios";
				// ios推送请求

				if (UmengPushNotification.PushType.unicast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendIOSUnicast(uNotify.getDeviceTokens(),
							uNotify.getMessage());
				} else if (UmengPushNotification.PushType.listcast.toString().equals(uNotify.getType())) {
					// 列播 要求不超过500个device_token
					respResult = uPushClient.sendIOSListcast(uNotify.getDeviceTokens(),
							uNotify.getMessage());
				} else if (UmengPushNotification.PushType.filecast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendIOSFilecast(uNotify.getMessage());
				} else if (UmengPushNotification.PushType.broadcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendIOSBroadcast(uNotify.getMessage());
				} else if (UmengPushNotification.PushType.groupcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendIOSGroupcast(uNotify.getMessage());
				} else if (UmengPushNotification.PushType.customizedcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendIOSCustomizedcast(uNotify.getAliasType(), uNotify.getAlias(),uNotify.getMessage());
				}
			}
		} catch (Exception ee) {
			//ee.printStackTrace();
			respResult = new UmengResponeResult();
			respResult.setRet("Fail");
			UmengResponeData data = new UmengResponeData();
			data.setError_code("IOS_PUSH_ERROR");
			data.setErrMsg(ee.getMessage());
			respResult.setData(data);
		}
		//-----------------------------------b
		log.info("开始："+platform+"平台友盟推送结果：" + JsonUtil.toJson(respResult));
		try {
			if (StringUtils.isBlank(uNotify.getPlatForm())
					|| UmengPushNotification.PlatFormType.android.toString().equals(uNotify.getPlatForm())) {// android推送请求
				UmengPushAndroidClient uPushClient = new UmengPushAndroidClient(uNotify.getDeviceTokens(),
						uNotify.getTicker(), uNotify.getTitle(), uNotify.getMessage(), uNotify.getAttach());
				platform = "android";//安卓
				if (UmengPushNotification.PushType.unicast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidUnicast(uNotify.getDeviceTokens(),uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				} else if (UmengPushNotification.PushType.listcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidListcast(uNotify.getDeviceTokens(),uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				} else if (UmengPushNotification.PushType.filecast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidFilecast(uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				} else if (UmengPushNotification.PushType.broadcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidBroadcast(uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				} else if (UmengPushNotification.PushType.groupcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidGroupcast(uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				} else if (UmengPushNotification.PushType.customizedcast.toString().equals(uNotify.getType())) {
					respResult = uPushClient.sendAndroidCustomizedcast(uNotify.getAliasType(), uNotify.getAlias(),uNotify.getTicker(),uNotify.getTitle(),uNotify.getTitle());
				}
			}
		} catch (Exception ee) {
			//ee.printStackTrace();
			respResult = new UmengResponeResult();
			respResult.setRet("Fail");
			UmengResponeData data = new UmengResponeData();
			data.setError_code("ANDROID_PUSH_ERROR");
			data.setErrMsg(ee.getMessage());
			respResult.setData(data);
		}

		log.info("结束："+platform+"平台友盟推送结果：" + JsonUtil.toJson(respResult));
		return respResult;
	}

	public static void main(String[] args) {
		System.out.println("".subSequence(0, 8));
	}
}
