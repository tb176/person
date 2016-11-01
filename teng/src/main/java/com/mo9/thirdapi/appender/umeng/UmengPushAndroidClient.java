package com.mo9.thirdapi.appender.umeng;

import java.util.HashMap;
import java.util.Map;

import com.mo9.thirdapi.appender.umeng.android.*;
import com.mo9.thirdapi.domain.UmengResponeResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class UmengPushAndroidClient extends UmengPushClientBase {
	private static Logger logger = Logger.getLogger(UmengPushAndroidClient.class);
	 /**本类唯一实体*/
    private static final UmengPushAndroidClient INSTANCE=new UmengPushAndroidClient();
    /**是否已经初始化*/
    private static   boolean inited;
	
	private static String appkey = null;
	private static String appMasterSecret = null;
	private String deviceTokens;// 设备唯一标示
	private String ticker;// 通知栏提示文字
	private String title;// 通知标题
	private String text;// 通知文字描述
	private boolean pushMode;//开发模式
	private Map<String, String> customizedFieldMap = new HashMap<String, String>();
	/**
	 * 当用户点击通知后，后续操作。 "go_app": 打开应用 "go_url": 跳转到URL "go_activity":
	 * 打开特定的activity "go_custom": 用户自定义内容。
	 */
	private String afterOpen;
	private String afterOpenAction;



	 /**
     * 获取本类唯一实例对象.
     *
     * @return
     */
    public static UmengPushAndroidClient instance()
    {
    	if(!inited)
        {
    		appkey = "521d8a7256240bcde5062c5e";
    		appMasterSecret = "msuzieyqq8daxthdlt4b2vxd6hdcsc85";
            inited=true;
        }
        return INSTANCE;
    }



	public UmengPushAndroidClient() {
		super();
	}



	public UmengPushAndroidClient(String deviceTokens, String ticker, String title, String text, String attach) {
		try {
			this.deviceTokens = deviceTokens;
			this.ticker = ticker;
			this.title = title;
			this.text = text;
			// 用户定义数据用于辅助通知到达时使用。如msgId:1343,msgType=BSN
			if (StringUtils.isNotBlank(attach)) {
				String[] customsList = attach.split("\\|");
				for (String cus : customsList) {
					String[] cusSubList = cus.split("#");
					if (cusSubList.length > 1) {
						// cusSubList[0]为key，cusSubList[1]为值
						customizedFieldMap.put(cusSubList[0], cusSubList[1]);
					}
				}

			}
			//从字典表中获取android appKey和secret ,注 android和ios不同
			appkey = "";
			appMasterSecret = "";
		  //获取开发模式，默认测试clone模式
		  String pMode = "";
		  if(StringUtils.isNotBlank(pMode)){
			  pushMode = Boolean.valueOf(pMode);
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UmengPushAndroidClient(String deviceTokens, String ticker, String title, String text, String attach,
			String afterOpen, String afterOpenAction) {
		try {
			this.deviceTokens = deviceTokens;
			this.ticker = ticker;
			this.title = title;
			this.text = text;
			this.setAfterOpen(afterOpen);
			this.setAfterOpenAction(afterOpenAction);
			// 用户定义数据用于辅助通知到达时使用。如msgId:1343,msgType=BSN
			if (StringUtils.isNotBlank(attach)) {
				String[] customsList = attach.split(",");
				for (String cus : customsList) {
					String[] cusSubList = cus.split(":");
					if (cusSubList.length > 1) {
						// cusSubList[0]为key，cusSubList[1]为值
						customizedFieldMap.put(cusSubList[0], cusSubList[1]);
					}
				}

			}
			appkey = "";
			appMasterSecret ="";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UmengResponeResult sendAndroidBroadcast(String ticker, String title, String text) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
		broadcast.setTicker(ticker);
		broadcast.setTitle(title);
		broadcast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			broadcast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				broadcast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				broadcast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				broadcast.setActivity(this.afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			broadcast.goAppAfterOpen();
		}
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode(pushMode);
		// Set customized fields
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			broadcast.setExtraField(mp.getKey(), mp.getValue());
		}
		return send(broadcast);
	}

	public UmengResponeResult sendAndroidUnicast(String deviceTokens, String ticker, String title, String text) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
		logger.info("===[sendAndroidUnicast]===appkey:"+appkey+",appMasterSecret:"+appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceTokens);
		unicast.setTicker(ticker);
		unicast.setTitle(title);
		unicast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			unicast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				unicast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				unicast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				unicast.setActivity(this.afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			unicast.goAppAfterOpen();
		}

		// unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode(pushMode);
		// Set customized fields
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			unicast.setExtraField(mp.getKey(), mp.getValue());
		}
		return send(unicast);
	}

	public UmengResponeResult sendAndroidListcast(String deviceTokens, String ticker, String title, String text) throws Exception {
		AndroidListcast listcast = new AndroidListcast(appkey, appMasterSecret);
		// TODO Set your device token
		listcast.setDeviceToken(deviceTokens);
		listcast.setTicker(ticker);
		listcast.setTitle(title);
		listcast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			listcast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				listcast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				listcast.setActivity(this.afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				listcast.setActivity(this.afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			listcast.goAppAfterOpen();
		}
		listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		listcast.setProductionMode(pushMode);
		// Set customized fields
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			listcast.setExtraField(mp.getKey(), mp.getValue());
		}
		return send(listcast);
	}

	public UmengResponeResult sendAndroidGroupcast(String ticker, String title, String text) throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"test"}, {"tag":"Test"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		logger.info(filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setTicker(ticker);
		groupcast.setTitle(title);
		groupcast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			groupcast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				groupcast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				groupcast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				groupcast.setActivity(afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			groupcast.goAppAfterOpen();
		}
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			groupcast.setExtraField(mp.getKey(), mp.getValue());
		}
		groupcast.setProductionMode(pushMode);
		return send(groupcast);
	}

	public UmengResponeResult sendAndroidCustomizedcast(String aliasType,String alias,String ticker, String title, String text) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(alias, aliasType);
		customizedcast.setTicker(ticker);
		customizedcast.setTitle(title);
		customizedcast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			customizedcast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				customizedcast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				customizedcast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				customizedcast.setActivity(afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			customizedcast.goAppAfterOpen();
		}
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode(pushMode);
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			customizedcast.setExtraField(mp.getKey(), mp.getValue());
		}
		return send(customizedcast);
	}

	public UmengResponeResult sendAndroidFilecast(String ticker, String title, String text) throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setTicker(ticker);
		filecast.setTitle(title);
		filecast.setText(text);
		if (StringUtils.isNotBlank(afterOpen)) {
			AndroidNotification.AfterOpenAction action = AndroidNotification.AfterOpenAction.valueOf(afterOpen);
			filecast.setAfterOpenAction(action);
			if (action.equals(AndroidNotification.AfterOpenAction.go_activity)) {
				filecast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_custom)) {
				filecast.setActivity(afterOpenAction);
			} else if (action.equals(AndroidNotification.AfterOpenAction.go_url)) {
				filecast.setActivity(afterOpenAction);
			}
		} else {
			// 默认打开应用程序
			filecast.goAppAfterOpen();
		}
		filecast.setProductionMode(pushMode);
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			filecast.setExtraField(mp.getKey(), mp.getValue());
		}
		return send(filecast);
	}

	public static void main(String[] args) {
		// TODO set your appkey and master secret here
		UmengPushAndroidClient demo = new UmengPushAndroidClient("521d8a7256240bcde5062c5e",
				"msuzieyqq8daxthdlt4b2vxd6hdcsc85", "", "", "", "", "");
		try {
//			demo.sendAndroidUnicast();
			/*
			 * TODO these methods are all available, just fill in some fields
			 * and do the test demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast(); demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast(); demo.sendAndroidFilecast();
			 *
			 * demo.sendIOSBroadcast(); demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast(); demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getAfterOpen() {
		return afterOpen;
	}

	public void setAfterOpen(String afterOpen) {
		this.afterOpen = afterOpen;
	}

	public String getAfterOpenAction() {
		return afterOpenAction;
	}

	public void setAfterOpenAction(String afterOpenAction) {
		this.afterOpenAction = afterOpenAction;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
