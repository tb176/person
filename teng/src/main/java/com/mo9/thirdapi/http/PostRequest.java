/**
 * 
 */
package com.mo9.thirdapi.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * Post请求
 * </p>
 * 
 * <p>
 * Post请求
 * </p>
 * 
 ******************************************************** 
 * Date Author Changes 2011-11-23 Eric Cao 创建
 ******************************************************** 
 */
public class PostRequest {

	private static Logger log = Logger.getLogger(PostRequest.class);

	/**
	 * Post请求(默认使用UTF-8)
	 * 
	 * @param url
	 *            请求的URL
	 * @param data
	 *            请求参数,待请求的参数采用name=value&name=value模式集成.
	 * @return 应答报文
	 * @throws IOException
	 *             如果请求失败，抛出该异常
	 */
	public static String postRequest(String uri, String data) throws IOException {

		log.info("===POST===uri="+uri);
		log.info("===data==="+data);
		trustAllHosts();// 信任所有HTTPS主机.
		URL url = new URL(uri);
		HttpURLConnection conn = null;
		StringBuffer resp = new StringBuffer();
		try {
			conn = (HttpURLConnection) url.openConnection();
			if ( url.getProtocol().equalsIgnoreCase("HTTPS") ) {// 为HTTPS地址，添加域名验证策略
				((HttpsURLConnection) conn).setHostnameVerifier(verifier);
			}
			if(url.getHost().contains("mo9.com") && uri.contains("proxydeduct/pay.mhtml")){
				log.info("post-代收-url	："+uri);
                //代收链接超时时间要延长
				conn.setConnectTimeout(5000);// 连接超时
                conn.setReadTimeout(30000);// 超时时限
			}else if(url.getHost().contains("mo9.com"))
			{/**mo9类部地址,仅仅允许2秒超时.*/
			    conn.setConnectTimeout(500);// 连接超时
                conn.setReadTimeout(4500);// 超时时限
			}
			else if((uri != null && uri.contains("pay_to_email=migugame%40139.com"))
			        || (data!= null && data.contains("pay_to_email=migugame%40139.com")))
			{
			    /**
                 * 江苏移动，需要把回调通知超时时长改到30s,
                 */
                conn.setConnectTimeout(5000);// 连接超时
                conn.setReadTimeout(30000);// 超时时限
			}
			else
			{
    			conn.setConnectTimeout(5000);// 5s超时
    			conn.setReadTimeout(8000);// 超时时限
			}
			conn.setRequestMethod("POST");
			// 这里是关键，表示我们要向链接里输出内容
			conn.setDoOutput(true);
			// 获得连接输出流
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			// 把数据写入
			StringBuffer param = new StringBuffer(data);
			if ( param.length() > 0 && param.charAt(0) == '&' ) {// Post请求，删除第一个'&符号'
				out.write(param.substring(1));
			} else {
				out.write(param.toString());
			}
			out.flush();
			out.close();
			// 到这里已经完成了，不过我们还是看看返回信息吧，他的注册返回信息也在此页面
			
			log.warn("----------------conn="+conn+",conndate"+conn.getDate());
			// Send the post request and get the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String inputLine = reader.readLine().toString();
			while (inputLine != null) {
				resp.append(inputLine);
				inputLine = reader.readLine();
			}
			reader.close();
		} finally {
			if ( conn != null ) {
				conn.disconnect();
			}
		}
		log.info("POST RESULT:"+resp.toString());
		return resp.toString();
	}
	
	public static String postRequest(String uri, String data,Integer timeout ) throws IOException {

		log.info("POST:"+uri+", msg:"+data);
		trustAllHosts();// 信任所有HTTPS主机.
		URL url = new URL(uri);
		HttpURLConnection conn = null;
		StringBuffer resp = new StringBuffer();
		try {
			conn = (HttpURLConnection) url.openConnection();
			if ( url.getProtocol().equalsIgnoreCase("HTTPS") ) {// 为HTTPS地址，添加域名验证策略
				((HttpsURLConnection) conn).setHostnameVerifier(verifier);
			}
			
			if(url.getHost().contains("mo9.com"))
			{/**mo9类部地址,仅仅允许2秒超时.*/
			    conn.setConnectTimeout(500);// 连接超时
                conn.setReadTimeout(4500);// 超时时限
			}
			else if((uri != null && uri.contains("pay_to_email=migugame%40139.com"))
                    || (data!= null && data.contains("pay_to_email=migugame%40139.com")))
            {
                /**
                 * 江苏移动，需要把回调通知超时时长改到30s,
                 */
                conn.setConnectTimeout(5000);// 连接超时
                conn.setReadTimeout(30000);// 超时时限
            }
			else
			{
				if(timeout == null || timeout == 0){
					timeout = 5000;// 5s超时
				}
    			conn.setConnectTimeout(timeout);
    			conn.setReadTimeout(8000);// 超时时限
			}
			conn.setRequestMethod("POST");
			// 这里是关键，表示我们要向链接里输出内容
			conn.setDoOutput(true);
			// 获得连接输出流
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			// 把数据写入
			StringBuffer param = new StringBuffer(data);
			if ( param.length() > 0 && param.charAt(0) == '&' ) {// Post请求，删除第一个'&符号'
				out.write(param.substring(1));
			} else {
				out.write(param.toString());
			}
			out.flush();
			out.close();
			// 到这里已经完成了，不过我们还是看看返回信息吧，他的注册返回信息也在此页面
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine = reader.readLine();
			while (inputLine != null) {
				resp.append(inputLine);
				inputLine = reader.readLine();
			}
			reader.close();
		} finally {
			if ( conn != null ) {
				conn.disconnect();
			}
		}
		log.info("POST RESULT:"+resp.toString());
		return resp.toString();
	}

	/**
	 * Post请求(默认使用UTF-8)
	 * 
	 * @param url
	 *            请求的URL
	 * @param params
	 *            请求参数
	 * @return 应答报文
	 * @throws IOException
	 *             如果请求失败，抛出该异常
	 */
	public static String postRequest(String uri, Map<String, String> params) throws IOException {

		return postRequest(uri, params, "UTF-8");
	}

	/**
	 * 按照指定的编码发起Post请求.
	 * 
	 * @param url
	 *            请求的URL
	 * @param params
	 *            请求参数
	 * @return 应答报文
	 * @throws IOException
	 *             如果请求失败，抛出该异常
	 */
	public static String postRequest(String uri, Map<String, String> params, String charSet) throws IOException {

		StringBuffer param = new StringBuffer();
		Set<String> keys = params.keySet();
		for ( String key : keys ) {
			// 将请求参数进行URL编码
			String value = URLEncoder.encode(params.get(key), charSet);
			param.append("&" + key + "=" + value);
		}
		return postRequest(uri, param.toString());
	}

	/**
	 * HTTS域名验证
	 */
	private static final HostnameVerifier verifier = new HostnameVerifier() {

		public boolean verify(String hostname, SSLSession session) {

			return true;
		}
	};

	/**
	 * HTTPS信任所有主机 trustAllHosts:
	 * 
	 */
	private static final void trustAllHosts() {

		TrustManager[] trustEverythingTrustManager = new TrustManager[] { new X509TrustManager() {

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				// TODO Auto-generated method stub
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				// TODO Auto-generated method stub

			}

			public X509Certificate[] getAcceptedIssuers() {

				// TODO Auto-generated method stub
				return null;
			}

		} };

		SSLContext sc;
		try {
			sc = SSLContext.getInstance("TLS");
			sc.init(null, trustEverythingTrustManager, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
	}

	public static void main(String[] arg) throws IOException {
		
	    Map<String,String> params = new HashMap<String,String>();
	    params.put("client_id", "S_admin");
	    params.put("valid_type", "secret");
	    params.put("valid_token", "11111111");
	    params.put("mobile", "15618550929");
	    params.put("template_name", "PINCODE");
	    params.put("template_data", "captcha:123456");
	    params.put("template_tags", "CN");
		String str = PostRequest.postRequest("http://localhost/snc/sms/sendSms", params);
		System.out.println(str);
	}
}
