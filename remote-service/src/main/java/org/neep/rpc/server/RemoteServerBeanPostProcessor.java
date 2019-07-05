package org.neep.rpc.server;

import org.neep.rpc.api.IRemoteClassType;
import org.neep.rpc.api.IRemoteServiceRegister;
import org.neep.rpc.common.InitializationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @Title RemoteServerBeanPostProcessor
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-30 下午9:59
 */
public class RemoteServerBeanPostProcessor implements BeanPostProcessor {

    private IRemoteServiceRegister remoteServiceRegister;
    //
    private boolean useGateway = true;

    public RemoteServerBeanPostProcessor(IRemoteServiceRegister remoteServiceRegister){
        this.remoteServiceRegister=remoteServiceRegister;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof IRemoteClassType){
            IRemoteClassType remoteBean = (IRemoteClassType)bean;
            this.remoteServiceRegister.register(remoteBean);
        }

        return bean;
    }
}
