package uMeng;

import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by beiteng on 2016/10/31.
 */
public class BaseTestCase {

    protected static ApplicationContext appContext = null;

    @BeforeClass
    public static void init()
    {
        appContext = new ClassPathXmlApplicationContext("classpath*:conf/snc-umeng.xml");
    }
}
