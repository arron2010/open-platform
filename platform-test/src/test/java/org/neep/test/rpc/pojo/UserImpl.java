package org.neep.test.rpc.pojo;

import org.neep.test.rpc.pojo.api.IUser;
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
@Service
public class UserImpl implements IUser {
    @Override
    public void login() {
        System.out.println("xiaopeng login");
    }
}
