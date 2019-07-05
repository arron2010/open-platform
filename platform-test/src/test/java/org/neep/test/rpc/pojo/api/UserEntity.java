package org.neep.test.rpc.pojo.api;

import lombok.Data;
import org.neep.rpc.msg.entity.RemoteEntity;

/**
 * @Title UserEntity
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午10:39
 */
@Data
public class UserEntity extends RemoteEntity {
    private String name;
    private String id;

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
