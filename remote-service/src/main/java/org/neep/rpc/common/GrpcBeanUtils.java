package org.neep.rpc.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.neep.proxy.api.ProxyProvider;
import org.neep.proxy.impl.cglib.CglibProvider;
import org.neep.rpc.server.ServiceHandler;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @Title GrpcBeanUtils
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 上午12:44
 */
public class GrpcBeanUtils {

    /**
     *@title
     *@description 创建动态代理对象，扩展将要注入到spring容器中grpc服务对象行为。实现服务自动绑定以及自动提交
     *@param
     *@return  java.lang.Object
     *@author  肖鹏
     *@createDate  19-6-8 下午6:43
     */

    public static Object instantiateBean(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner,Object src){
        Class<?>[] interfaces =bd.getBeanClass().getInterfaces();
        Set<Class<?>> proxyInterfaces = Sets.newLinkedHashSet();
        for (int i =0;i < interfaces.length;i++){
            Annotation annotation = interfaces[i].getAnnotation(RemoteService.class);
            if (annotation != null){
                proxyInterfaces.add(interfaces[i]);
            }
        }
        Preconditions.checkState(proxyInterfaces.size()>0,"Grpc服务接口缺少RemoteService注解");
        ServiceHandler serviceHandler = new ServiceHandler(proxyInterfaces.toArray(new Class<?>[proxyInterfaces.size()]));
        proxyInterfaces.add(io.grpc.BindableService.class);
        Class<?>[]  proxyClazz =proxyInterfaces.toArray(new Class[proxyInterfaces.size()]);

        ProxyProvider cglibProvider = new CglibProvider();
        Object cglibObj = cglibProvider.createInterceptorProxy(src, serviceHandler,proxyClazz);
        return cglibObj;
    }
}
