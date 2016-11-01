package com.mo9.thirdapi.domain;

/**
 * 友盟推送实体
 */

public class UmengPushNotification extends  Notification{
	// 消息推送类别
	public enum PlatFormType {
		ios, android
	}

	// 消息推送类别
	public enum PushType {
		unicast, // 单播
		listcast, // 列播(要求不超过500个device_token)
		filecast, // 文件播 (多个device_token可通过文件形式批量发送）
		broadcast, // 广播
		groupcast, // 组播
		customizedcast// 通过开发者自有的alias进行推送
	}

	// 推送平台:android|ios ，不填两个平台同时推送
	private String platForm;

	/**
	 * 必填 消息发送类型,其值可以为: unicast-单播 listcast-列播(要求不超过500个device_token)
	 * filecast-文件播 (多个device_token可通过文件形式批量发送） broadcast-广播 groupcast-组播
	 * (按照filter条件筛选特定用户群, 具体请参照filter参数) customizedcast(通过开发者自有的alias进行推送),
	 * 包括以下两种case: - alias: 对单个或者多个alias进行推送 - file_id:
	 * 将alias存放到文件后，根据file_id来推送
	 */
	private String type;
	/**
	 * 可选 设备唯一表示 当type=unicast时,必填, 表示指定的单个设备 当type=listcast时,必填,要求不超过500个,
	 * 多个device_token以英文逗号间隔
	 */
	private String deviceTokens;
	// 可选 当type=customizedcast时，必填，alias的类型
	private String aliasType ;
	// 可选 当type=customizedcast时, 开发者填写自己的alias。
	private String alias;
	// 必填 通知栏提示文字
	private String ticker;
	// 必填 通知标题
	private String title;
	// 辅助信息
	private String attach;
	//提示信息
	private String alert;
	/**
	 * 消息内容
	 */
	private String text;
	// 签名
	private String sign;

	public UmengPushNotification() {
		this.setPlatForm("");
		this.setDeviceTokens("");
		this.setTicker("");
		this.setTitle("");
		this.setText("");
		this.setMessage("");
		this.setAttach("");
		this.setAliasType("");
		this.setAlias("");
		this.setAlert("");
	}

	
	public String getAlert() {
		return alert;
	}


	public void setAlert(String alert) {
		this.alert = alert;
	}


	public String getPlatForm() {
		return platForm;
	}

	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeviceTokens() {
		return deviceTokens;
	}

	public void setDeviceTokens(String deviceTokens) {
		this.deviceTokens = deviceTokens;
	}

	public String getAliasType() {
		return aliasType;
	}

	public void setAliasType(String aliasType) {
		this.aliasType = aliasType;
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

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getText() {
		return text;
	}


	public void setText(String text) {
		// 父类消息体赋值
		this.setMessage(text);
		this.text = text;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
