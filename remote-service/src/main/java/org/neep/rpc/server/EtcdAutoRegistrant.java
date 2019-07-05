package org.neep.rpc.server;


import com.google.common.base.Splitter;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.services.HealthStatusManager;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.config.GrpcChannelsProperties;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.neep.etcd.registry.EtcdRegistration;
import org.neep.etcd.registry.EtcdServiceRegistry;
import org.neep.rpc.anno.EtcdRegistry;
import org.neep.rpc.anno.RemotePost;
import org.neep.rpc.anno.RemoteService;
import org.neep.rpc.api.IRemoteClassType;
import org.neep.rpc.api.IRemoteServiceRegister;
import org.neep.rpc.common.GrpcHelper;
import org.neep.utils.exceptions.EtcdOperationException;
import org.neep.utils.exceptions.RemoteBeansException;
import org.neep.utils.reflect.ReflectionHelper;
import org.neep.utils.tools.NetWorkAddressUtils;
import org.neep.utils.tools.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.net.URI;
import java.util.List;

/**
 * @Title EtcdAtuoRegistrant
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-2-2 下午11:11
 */

@RequiredArgsConstructor
public class EtcdAutoRegistrant implements IRemoteServiceRegister {

    private static final Logger log = LoggerFactory.getLogger(EtcdAutoRegistrant.class);

    /*
    　服务注册信息从application.yml文件获取
     */
//    @Value("${USE_GRPC_YAML_CONFIG:false}")
//    private boolean useConfig=false;



    private final EtcdServiceRegistry etcdServiceRegistry;
    private final GrpcChannelsProperties properties;
    private final  HealthStatusManager healthStatusManager;
    private final GrpcServerProperties grpcServerProperties;


    private String getServiceName(Class<?> serviceClass) {
        String serviceName = GrpcHelper.getRegisterSvcName(serviceClass);
        return serviceName;
    }

    private EtcdRegistration createEtcdRegistration(String className, RegisterInfo registerInfo){

        int port = 9000;
        String ipAddress ="localhost";
        String centerName = null;
        if ( registerInfo.getCenterName() != null && !registerInfo.getCenterName().isEmpty()) {
            centerName = registerInfo.getCenterName();
        }else{
            centerName =etcdServiceRegistry.getProperties().getCenter();
        }

        if (etcdServiceRegistry.getProperties().isAutoIp()){
            port = grpcServerProperties.getPort();
            ipAddress =NetWorkAddressUtils.findLocalHostAddress();
        }else{
            //把yaml文件下grpc/client节点配置信息，作为服务注册信息配置
            URI uri =this.properties.getChannel(centerName).getAddress();
            if (uri == null){
                throw new RemoteBeansException(StringHelper.wrapText(centerName)+"服务地址注册信息未配置");
            }
            port=uri.getPort();
            ipAddress=uri.getHost();
        }
        EtcdRegistration etcdRegistration = new EtcdRegistration(className, ipAddress,port,registerInfo.getWeight(),centerName);
        return etcdRegistration;
    }

    public  synchronized void register(Object bean) throws EtcdOperationException {
        IRemoteClassType remoteBean = (IRemoteClassType)bean;
        this.register(remoteBean.getClassType());

//        Class<?> serviceClass = ReflectionHelper.findInterfaceByAnno(bean.getClass(),RemoteService.class);
//        if (serviceClass != null){
//        }
    }
    private void register(Class<?> serviceClass) throws EtcdOperationException{
        RegisterInfo registerInfo = null;
        EtcdRegistry etcdRegistry = AnnotationUtils.findAnnotation(serviceClass,EtcdRegistry.class);
        if (etcdRegistry == null){
            RemotePost remotePost = AnnotationUtils.findAnnotation(serviceClass,RemotePost.class);
            if (remotePost != null){
                registerInfo = new RegisterInfo(remotePost);
            }else{
                return;
            }
        }else{
            registerInfo = new RegisterInfo(etcdRegistry);
        }
        Class<?> remoteClass = ReflectionHelper.findInterfaceByAnno(serviceClass,RemoteService.class);
        if (remoteClass != null){
            String serviceName = getServiceName(remoteClass);
            EtcdRegistration etcdRegistration = this.createEtcdRegistration(serviceName,registerInfo);
            etcdServiceRegistry.register(etcdRegistration);
            healthStatusManager.setStatus(serviceName,HealthCheckResponse.ServingStatus.SERVING);
        }
    }

    private class RegisterInfo{
        private String centerName;
        private int weight;
        public RegisterInfo(EtcdRegistry etcdRegistry){
            this.centerName=etcdRegistry.value();
            this.weight= etcdRegistry.weight();
        }

        public RegisterInfo(RemotePost remotePost){
            this.centerName=remotePost.value();
            this.weight= remotePost.weight();
        }


        public String getCenterName() {
            return centerName;
        }

        public int getWeight() {
            return weight;
        }

    }
//    private synchronized void registerService(ApplicationContext context) {
//
//        Collection<String> beanNames =
//                Arrays.asList(context.getBeanNamesForAnnotation(EtcdRegistry.class));
//        Map<String,String> cache = new ConcurrentHashMap<>();
//        for (String beanName : beanNames) {
//            Object bean = context.getBean(beanName);
//           EtcdRegistry etcdRegistry = context.findAnnotationOnBean(beanName, EtcdRegistry.class);
//            String serviceName = getServiceName(bean);
//             //Tools.parseServiceName(fieldValue.toString());
//            if (serviceName == null || serviceName.isEmpty()){
//                continue;
//            }
//            String className = serviceName;
//            if (!cache.containsKey(className)){
//                EtcdRegistration etcdRegistration = this.createEtcdRegistration(className,etcdRegistry);
//                etcdServiceRegistry.register(etcdRegistration);
//                healthStatusManager.setStatus(etcdRegistry.value(),HealthCheckResponse.ServingStatus.SERVING);
//                cache.put(className,Integer.toString(bean.hashCode()));
//            }else{
//                throw new RuntimeException("不能重复注册服务名：【"+className+"】详细类名：【"+bean.getClass().getName()+"】");
//            }
//        }
//        cache = null;
//    }
}
