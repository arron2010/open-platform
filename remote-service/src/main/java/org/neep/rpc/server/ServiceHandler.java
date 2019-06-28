package org.neep.rpc.server;


import io.grpc.BindableService;
import org.neep.proxy.api.Interceptor;
import org.neep.proxy.api.Invocation;
import org.neep.utils.reflect.ReflectionHelper;

/**
 * @Title ServiceHandler
 * @Description 用来拦截远程服务实现类的方法，处理bindservice以及提交grpc消息
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 上午10:25
 */
public class ServiceHandler implements Interceptor {


    /*
    远程服务接口类型
     */
    private final Class<?>[] remoteInterfaces;

    public ServiceHandler(Class<?>[] remoteInterfaces) {
        this.remoteInterfaces = remoteInterfaces;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        //拦截代理对象BindService调用，委托给具体实现类
        boolean foundBindService = ReflectionHelper.existMethod(invocation.getMethod(),BindableService.class);
        if (foundBindService){
            BindableService  bindableService = new ServiceBinder(invocation);
            return bindableService.bindService();
        }

//        //拦截代理对象远程调用方法，委托给提交者，进行消息提交
//        boolean remoteMethodFound = ReflectionHelper.existMethod(invocation.getMethod(),  this.remoteInterfaces );
//        if (remoteMethodFound){
//            ServiceCommitter committer = new ServiceCommitter(invocation);
//            return committer.commit();
//        }

        Object result = invocation.proceed();
        return result;
    }
}
