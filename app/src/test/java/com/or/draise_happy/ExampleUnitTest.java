package com.or.draise_happy;

import org.junit.Test;

import java.net.InetAddress;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <ic_home_meun_member href="http://d.android.com/tools/testing">Testing documentation</ic_home_meun_member>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        InetAddress[]   inetAdds   =   InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
        for(int i = 0 ; i < inetAdds.length; i++){
            System.out.print(inetAdds[i].getHostName()+ ":\t");
            System.out.println(inetAdds[i].getHostAddress());
        }
        String str = "您的IP是：[58.132.182.39] 来自：北京市 教育信息网";
        System.out.println(str.substring(7,20));
    }
}