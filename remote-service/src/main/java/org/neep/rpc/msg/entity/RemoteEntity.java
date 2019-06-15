package org.neep.rpc.msg.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title RemoteEntity
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 上午11:16
 */
@Data
public abstract class RemoteEntity implements Serializable {
    private boolean success = true;
    private String message ="";

    public RemoteEntity() {
    }


}
