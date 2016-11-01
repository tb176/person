package uMeng;

import com.mo9.thirdapi.appender.umeng.UmengNotificationAppender;
import com.mo9.thirdapi.appender.umeng.UmengPushIosClient;
import com.mo9.thirdapi.domain.UmengPushNotification;
import org.junit.Test;


public class UmengIosTest extends BaseTestCase{
	private String androidDeviceToken = "AoS2toF88khQq3MFqTL2HOHbR3esZIIgWDHFj4myKLXR";// android设备唯一标示
	private String isoDeviceToken = "968278ecc82a16282735610a7d1fdd80570e2d41233e6407fa47e9b995f6818b";//ios设备标识
	private String deviceTokens = androidDeviceToken+","+isoDeviceToken;//

	//单播测试
	@Test
	public void sendUnicastTest(){
		try {
			UmengPushIosClient.instance().sendIOSUnicast(isoDeviceToken,"您的江湖救急借款申请已通过审核。我们将立即放款至您的银行账户，请注意查收~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void test(){
		
          	UmengNotificationAppender appender = new UmengNotificationAppender();
  			 // 推送消息
          	UmengPushNotification message = new UmengPushNotification();
          	//message.setPlatForm(PlatFormType.ios.name());//设置消息平台
          	//message.setPlatForm(PlatFormType.android.name());//设置消息平台
          	message.setDeviceTokens(deviceTokens);
          	message.setAlias("15395182106");
          	message.setAliasType("mobile");
          	//自定义推送方式，以tag为关键字
          	//message.setType(UmengPushNotification.PushType.customizedcast.toString());//自定义播
          	message.setType(UmengPushNotification.PushType.listcast.name());// 单播
          	message.setTicker("[mo9]消息通知~");//通知栏提示文字
          	message.setTitle("放款消息~");// 通知标题
          //滑动通知栏看到的内容
          	message.setText("您的江湖救急借款申请已通过审核。我们将立即放款至您的银行账户，请注意查收。");// 通知文字描述
          	message.setAlert("您的江湖救急借款申请已通过审核。我们将立即放款至您的银行账户，请注意查收。");
    		
          	message.setAttach("msgType#HUITIE");
          	appender.doPush(message);
		
		
	}

	
}
