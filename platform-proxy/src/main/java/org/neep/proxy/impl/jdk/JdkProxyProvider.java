package org.neep.proxy.impl.jdk;


import org.neep.proxy.api.Interceptor;
import org.neep.proxy.api.Invocation;
import org.neep.proxy.api.ObjectInvoker;
import org.neep.proxy.api.ObjectProvider;
import org.neep.proxy.impl.ProviderTemplate;
import org.neep.utils.reflect.ReflectionHelper;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class JdkProxyProvider extends ProviderTemplate {

    @Override
    public <T> T createDelegatorProxy(ClassLoader classLoader, ObjectProvider<?> delegateProvider,
                                      Class<?>... proxyClasses) {

        T result = (T) Proxy.newProxyInstance(classLoader, proxyClasses, new DelegatorInvocationHandler(delegateProvider));

        return result;
    }

    @Override
    public <T> T createInterceptorProxy(ClassLoader classLoader, Object target, Interceptor interceptor,
                                        Class<?>... proxyClasses) {

        T result = (T) Proxy.newProxyInstance(classLoader, proxyClasses, new InterceptorInvocationHandler(target,
                        interceptor));

        return result;
    }

    @Override
    public <T> T createInvokerProxy(ClassLoader classLoader, ObjectInvoker invoker, Class<?>... proxyClasses) {
        T result = (T) Proxy.newProxyInstance(classLoader, proxyClasses, new InvokerInvocationHandler(invoker));

        return result;
    }

    private abstract static class AbstractInvocationHandler implements InvocationHandler, Serializable {

        private static final long serialVersionUID = -5735923917525983672L;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (ReflectionHelper.isHashCode(method)) {
                return Integer.valueOf(System.identityHashCode(proxy));
            }

            if (ReflectionHelper.isEqualsMethod(method)) {
                return Boolean.valueOf(proxy == args[0]);
            }

            return invokeImpl(proxy, method, args);
        }

        protected abstract Object invokeImpl(Object proxy, Method method, Object[] args) throws Throwable;
    }

    private static class DelegatorInvocationHandler extends AbstractInvocationHandler {

        private static final long serialVersionUID = -7039084673621066448L;

        private final ObjectProvider<?> delegateProvider;

        protected DelegatorInvocationHandler(ObjectProvider<?> delegateProvider) {
            this.delegateProvider = delegateProvider;
        }

        @Override
        public Object invokeImpl(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                return method.invoke(delegateProvider.getObject(), args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }

    private static class InterceptorInvocationHandler extends AbstractInvocationHandler {

        private static final long serialVersionUID = -5079758027748139728L;

        private final Object target;
        private final Interceptor methodInterceptor;

        public InterceptorInvocationHandler(Object target, Interceptor methodInterceptor) {
            this.target = target;
            this.methodInterceptor = methodInterceptor;
        }

        @Override
        public Object invokeImpl(Object proxy, Method method, Object[] args) throws Throwable {
            ReflectionInvocation invocation = new ReflectionInvocation(proxy, target, method, args);
            return methodInterceptor.intercept(invocation);
        }
    }

    public static class InvokerInvocationHandler extends AbstractInvocationHandler {

        private static final long serialVersionUID = 4277344818056348876L;

        private final ObjectInvoker invoker;

        public InvokerInvocationHandler(ObjectInvoker invoker) {
            this.invoker = invoker;
        }

        @Override
        public Object invokeImpl(Object proxy, Method method, Object[] args) throws Throwable {
            return invoker.invoke(proxy, method, args);
        }
    }

    private static class ReflectionInvocation implements Invocation {
        private final Object proxy;
        private final Object target;
        private final Method method;
        private  Object[] arguments;

        public ReflectionInvocation(Object proxy, Object target, Method method, Object[] arguments) {
            this.proxy = proxy;
            this.target = target;
            this.method = method;
            if (arguments == null){
                arguments = new  Object[0];
            }else{
                arguments = arguments.clone();
            }
        }

        @Override
        public Object[] getArguments() {
            return arguments;
        }

        @Override
        public Method getMethod() {
            return method;
        }

        @Override
        public Object getProxy() {
            return proxy;
        }

        @Override
        public Object proceed() throws Throwable {
            try {
                return method.invoke(target, arguments);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
