package org.neep.rpc.api;

import org.neep.utils.exceptions.EtcdOperationException;

public interface IRemoteServiceRegister {
    void register(Object bean) throws EtcdOperationException;
}
