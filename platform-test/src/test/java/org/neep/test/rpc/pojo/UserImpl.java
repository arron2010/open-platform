package org.neep.test.rpc.pojo;

import net.devh.boot.grpc.server.service.GrpcService;
import org.neep.rpc.anno.EtcdRegistry;
import org.neep.rpc.anno.RemotePost;
import org.neep.test.rpc.pojo.api.IUser;
import org.neep.test.rpc.pojo.api.UserEntity;
import org.springframework.stereotype.Service;

/**
 * @Title UserImpl
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-12 下午10:23
 */
@RemotePost
public class UserImpl implements IUser {
    public UserImpl() {
    }

    @Override
    public UserEntity login(UserEntity userEntity) {
        System.out.println(userEntity.getName());
        userEntity.setName(userEntity.getName()+"----------------->handle");
        return userEntity;
    }
}
