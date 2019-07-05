package org.neep.test.rpc.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neep.rpc.testing.SpringBootTestContextBootstrapperEx;
import org.neep.test.rpc.RpcStartApp;
import org.neep.test.rpc.pojo.api.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title ServerTest
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-12 下午8:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpcStartApp.class)
@BootstrapWith(SpringBootTestContextBootstrapperEx.class)
public class ServerTest {

    @Autowired
    @Qualifier("user#proxy")
    private IUser user;

    @Autowired
    @Qualifier("userImpl")
    private IUser user1;
    @Test
    public  void testServerProxy(){
//        user.login();
//        user1.login();
    }
}
