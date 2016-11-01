package com.mo9.thirdapi.appender.umeng;

import java.util.HashMap;
import java.util.Map;

import com.mo9.thirdapi.appender.umeng.ios.*;
import com.mo9.thirdapi.domain.UmengResponeResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;



public class UmengPushIosClient extends UmengPushClientBase {
	private static Logger logger = Logger.getLogger(UmengPushIosClient.class);
	
	private static final UmengPushIosClient INSTANCE = new UmengPushIosClient();
	private static   boolean inited;//是否初始化
	private static String appkey = null;
	private static String appMasterSecret = null;
	private String deviceTokens;//设备标识
	private String alert;//通知栏
	private String attach;//标签
	private boolean pushMode;////开发模式
	private Map<String, String> customizedFieldMap = new HashMap<String, String>();
	
	 /**
     * 获取本类唯一实例对象. 
     *  
     * @return
     */
    public  static UmengPushIosClient instance()
    {
    	if(!inited)
        {
    		appkey = "525f3bdf56240b4bc90d74cf";//我们可以存入字典表 可配置
    		appMasterSecret = "vah1ozlhqtjfcn1nilmhkissgbyk1ust";
    		logger.info("===[UmengPushIosClient]===appkey="+appkey+",appMasterSecret="+appMasterSecret);
            inited=true;
        }
    	return INSTANCE;
    }
	
	

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	

	public UmengPushIosClient() {
		super();
	}

	public UmengPushIosClient(String deviceTokens, String alert, String attach) {
		try {
			this.deviceTokens = deviceTokens;
			this.alert = alert;
			this.attach = attach;
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
			appkey = "";
			appMasterSecret = "";
		logger.info("===[UmengPushIosClient]===appkey="+appkey+",appMasterSecret="+appMasterSecret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UmengResponeResult sendIOSBroadcast(String alert) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);
		broadcast.setAlert(alert);
		broadcast.setBadge(0);
		broadcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		//broadcast.setTestMode();
		broadcast.setProductionMode(pushMode);
		// Set customized fields
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			broadcast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		return send(broadcast);
	}

	public UmengResponeResult sendIOSUnicast(String deviceTokens, String alert) throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
		unicast.setDeviceToken(deviceTokens);
		unicast.setAlert(alert);
		unicast.setBadge(0);
		unicast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		unicast.setTestMode();
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			unicast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		// Set customized fields
		// unicast.setCustomizedField("test", "helloworld");
		return send(unicast);
	}

	public UmengResponeResult sendIOSListcast(String deviceTokens, String alert) throws Exception {
		IOSListcast listcast = new IOSListcast(appkey, appMasterSecret);
		// TODO Set your device token
		listcast.setDeviceToken(deviceTokens);
		listcast.setAlert(alert);
		listcast.setBadge(0);
		listcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		listcast.setProductionMode(pushMode);
		// Set customized fields
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			listcast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		return send(listcast);
	}

	public UmengResponeResult sendIOSGroupcast(String alert) throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey, appMasterSecret);
		/*
		 * TODO Construct the filter condition: "where": { "and": [
		 * {"tag":"iostest"} ] }
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());

		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert(alert);
		groupcast.setBadge(0);
		groupcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			groupcast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		// mode
		groupcast.setProductionMode(pushMode);
		return send(groupcast);
	}

	public UmengResponeResult sendIOSCustomizedcast(String aliasType,String alias,String alert) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey, appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them
		// if there are multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		customizedcast.setAlias(alias, aliasType);
		customizedcast.setAlert(alert);
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		//customizedcast.setProductionMode();
		customizedcast.setProductionMode(pushMode);
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			customizedcast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		return send(customizedcast);
	}

	public UmengResponeResult sendIOSFilecast(String alert) throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there
		// are multiple tokens
		String fileId = uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
		filecast.setFileId(fileId);
		filecast.setAlert(alert);
		filecast.setBadge(0);
		filecast.setSound("default");
		// TODO set 'production_mode' to 'true' if your app is under production
		// mode
		filecast.setProductionMode(pushMode);
		// 用户自定义内容
		for (Map.Entry<String, String> mp : customizedFieldMap.entrySet()) {
			filecast.setCustomizedField(mp.getKey(), mp.getValue());
		}
		return send(filecast);
	}

	public static void main(String[] args) {
		// TODO set your appkey and master secret here
		UmengPushIosClient demo = new UmengPushIosClient("521d8a7256240bcde5062c5e",
				"msuzieyqq8daxthdlt4b2vxd6hdcsc85", "");
		try {
			//demo.sendIOSBroadcast();
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
}
