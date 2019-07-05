package org.neep.test.rpc.pojo.api;

import org.neep.rpc.anno.RemoteService;

@RemoteService
public interface IUser {
    UserEntity login(UserEntity userEntity);
}
