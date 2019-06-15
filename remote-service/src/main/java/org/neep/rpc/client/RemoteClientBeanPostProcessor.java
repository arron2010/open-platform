package org.neep.rpc.client;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import org.assertj.core.util.Strings;
import org.neep.rpc.anno.RemoteClient;
import org.neep.rpc.anno.RemoteService;
import org.neep.utils.exceptions.RemoteBeansException;
import org.neep.utils.tools.StringHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title RemoteClientBeanPostProcessor
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午2:39
 */
public class RemoteClientBeanPostProcessor implements BeanPostProcessor {

    protected final ApplicationContext applicationContext;
    protected GrpcChannelFactory channelFactory = null;

    public RemoteClientBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        do{
            for (final Field field : clazz.getDeclaredFields()) {
                RemoteClient annotation = AnnotationUtils.findAnnotation(field, RemoteClient.class);
                if (annotation != null) {
                    if (Strings.isNullOrEmpty(annotation.value())){
                        throw new RemoteBeansException("字段"+field.getName()+"RemoteClient注解值不能为空");
                    }
                    RemoteService remoteService = AnnotationUtils.findAnnotation(field.getType(),RemoteService.class);
                    if (remoteService == null){
                        throw new RemoteBeansException("字段"+field.getName()+"声明必须为一个带有RemoteService注解的接口");
                    }
                    final Class<?> serviceType = field.getType();
                    final String path = this.createPath(annotation.value(),serviceType);
                    //暂时创建空ClientInterceptor List
                    final Channel chanel = this.createChannel(path,new ArrayList<ClientInterceptor>());
                    this.setChanelField(serviceType,chanel);
                }
            }
            clazz = clazz.getSuperclass();
        }while (clazz != null);
        return bean;
    }

    private String createPath(String centerName,Class<?> clazz){
        String className = StringHelper.removeInterfacePrefix(ClassUtils.getShortName(clazz));
        String path = String.format("/%s/%s/%s","services",centerName,
                StringHelper.capitalFirst(className));
        return path;
    }

    private Channel createChannel(String path, List<ClientInterceptor> interceptors){
        final Channel channel = getChannelFactory().createChannel(path.trim(), interceptors);
       return channel;
    }

    private void  setChanelField(Class<?> serviceType,Channel channel){
        String beanName = StringHelper.getBeanNameByClass(serviceType.getName());
        Object serviceObj = this.applicationContext.getBean(beanName);
        if (serviceObj == null ){
            throw new RemoteBeansException(beanName+"对象不存在");
        }
        Field field = ReflectionUtils.findField(serviceObj.getClass(),"channel");
        if (field == null){
            throw new RemoteBeansException(beanName+"对象无chanel字段");
        }
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, serviceObj, channel);
    }


    private GrpcChannelFactory getChannelFactory() {
        if (this.channelFactory == null) {
            GrpcChannelFactory factory = (GrpcChannelFactory)this.applicationContext.getBean(GrpcChannelFactory.class);
            this.channelFactory = factory;
            return factory;
        } else {
            return this.channelFactory;
        }
    }
}
