package uMeng;

import com.mo9.thirdapi.appender.umeng.UmengPushAndroidClient;
import com.mo9.thirdapi.domain.UmengResponeResult;
import org.junit.Test;


public class UmengAndroidTest extends BaseTestCase{
	private String deviceTokens = "AoS2toF88khQq3MFqTL2HOHbR3esZIIgWDHFj4myKLXR";// 设备唯一标示
	private String ticker = "[mo9]消息通知~";// 通知栏提示文字
	private String title = "放款消息~";// 通知标题
	private String text =  "您的江湖救急借款申请已通过审核。我们将立即放款至您的银行账户，请注意查收。";// 通知文字描述
	private String attach = "msgId:1343,msgType=BSN";// 用户定义数据用于辅助通知到达时使用。如msgId:1343,msgType=BSN
	private boolean pushMode = false;//开发模式
	private String afterOpen = "";
	/**
	 * go_app,//打开应用
	 * go_url,//跳转到URL
	 * go_activity,//打开特定的activity
	 * go_custom//用户自定义内容。
	 */
	private String afterOpenAction = "";
	@Test
	public void test(){
		//UmengPushAndroidClient demo = new UmengPushAndroidClient(deviceTokens,ticker,title ,text, attach, afterOpen, afterOpenAction);
		try {
//			UmengResponeResult result = demo.sendAndroidUnicast(deviceTokens,ticker,title ,text);//单播(unicast): 向指定的设备发送消息，包括向单个device_token或者单个alias发消息。
//			System.out.println("-----result="+result.getRet());
			String aliasType = "mobile";
			String alias = "15395182106";
			//UmengResponeResult result = UmengPushAndroidClient.instance().sendAndroidCustomizedcast(aliasType,alias,ticker,title ,text);
			UmengResponeResult result = UmengPushAndroidClient.instance().sendAndroidUnicast(deviceTokens, ticker, title, text);//单播测试
			System.out.println("-----result="+result.getRet());
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
