package org.neep.test.rpc.server;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neep.rpc.anno.RemoteClient;
import org.neep.rpc.testing.SpringBootTestContextBootstrapperEx;
import org.neep.test.rpc.RpcStartApp;
import org.neep.test.rpc.pojo.api.IUser;
import org.neep.test.rpc.pojo.api.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Title GrpcTest01
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午1:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpcStartApp.class)
@BootstrapWith(SpringBootTestContextBootstrapperEx.class)
public class GrpcTest01 {

    //@GrpcClient("platformtest")
    @Autowired
    @RemoteClient("platformtest")
    private IUser user;

    @Test
    public void testClient(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName("xiaopeng");
        user.login(userEntity);
    }

}
