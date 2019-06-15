package org.neep.utils.exceptions;

import org.springframework.beans.BeansException;

/**
 * @Title RemoteBeansException
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午4:24
 */
public class RemoteBeansException extends BeansException {
    public RemoteBeansException(String msg) {
        super(msg);
    }

    public RemoteBeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
