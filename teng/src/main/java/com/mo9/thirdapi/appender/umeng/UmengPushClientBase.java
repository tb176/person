package com.mo9.thirdapi.appender.umeng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


import com.mo9.thirdapi.domain.UmengResponeData;
import com.mo9.thirdapi.domain.UmengResponeResult;
import com.mo9.thirdapi.http.PostRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import net.sf.json.JSONObject;


public class UmengPushClientBase {
	private Logger log = Logger.getLogger(UmengPushClientBase.class);
	// The host
	private String host = "http://msg.umeng.com";
	// The user agent
	protected final String USER_AGENT = "Mozilla/5.0";
	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();

	// The upload path
	protected static final String uploadPath = "/upload";

	// The post path
	protected static final String postPath = "/api/send";
	// then status query path
	protected static final String statusQueryPath = "/api/status";

	public UmengPushClientBase() {
		String hostUrl = "";
		if (StringUtils.isNotBlank(hostUrl)) {
			host = hostUrl;
		}
	}
	//	发送消息
	public UmengResponeResult send(UmengNotification msg) throws Exception {
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		String url = host + postPath;
		String postBody = msg.getPostBody();
		log.info("---postBody---="+msg.getPostBody());
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8"));
		url = url + "?sign=" + sign;
		// 报500错误原因：
		// 1.请检查友盟网，android和ios 推送服务器 服务器IP地址是否正确
		// 2.另请确认必须参数是否为空
		// 3.如果android,ios客户端配置参数不对也会引起，如：customizedcast自定义推送消息，如果ios，aliasType与alias参数反了同样会出现这样问题。
		// 3.1.还有就是使用自定义，服务器上对应的tag找不到也会出现
		String result = PostRequest.postRequest(url, postBody);
		
		JSONObject respJson = JSONObject.fromObject(result);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("data", UmengResponeData.class);
		UmengResponeResult resResult = (UmengResponeResult) JSONObject.toBean(respJson, UmengResponeResult.class,
				classMap);
		log.info("===有盟推送信息结果返回===resResult=" + resResult);
		return resResult;
	}

	// Upload file with device_tokens to Umeng
	public String uploadContents(String appkey, String appMasterSecret, String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer.toString((int) (System.currentTimeMillis() / 1000));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = host + uploadPath;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = JSONObject.fromObject(result);
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue

		return fileId;
	}

}
