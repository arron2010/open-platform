package org.neep.proxy.impl;


import com.google.common.collect.Sets;
import org.neep.proxy.api.Interceptor;
import org.neep.proxy.api.ObjectInvoker;
import org.neep.proxy.api.ObjectProvider;
import org.neep.proxy.api.ProxyProvider;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;

public abstract class ProviderTemplate implements ProxyProvider {

    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    @Override
    public <T> T createDelegatorProxy(ObjectProvider<?> delegateProvider, Class<?>... proxyClasses) {
        return createDelegatorProxy(Thread.currentThread().getContextClassLoader(), delegateProvider, proxyClasses);
    }

    @Override
    public <T> T createInterceptorProxy(Object target, Interceptor interceptor, Class<?>... proxyClasses) {
        return createInterceptorProxy(Thread.currentThread().getContextClassLoader(),
                target, interceptor, proxyClasses);
    }

    @Override
    public <T> T createInvokerProxy(ObjectInvoker invoker, Class<?>... proxyClasses) {
        return createInvokerProxy(Thread.currentThread().getContextClassLoader(), invoker, proxyClasses);
    }

    private  boolean hasDefaultConstructor(Class<?> superclass) {
        final Constructor<?>[] declaredConstructors = superclass.getDeclaredConstructors();
        for (int i = 0; i < declaredConstructors.length; i++) {
            Constructor<?> constructor = declaredConstructors[i];
            if (constructor.getParameterTypes().length == 0
                    && (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor
                    .getModifiers()))) {
                return true;
            }
        }
        return false;
    }

    private  Class<?>[] toNonInterfaces(Class<?>[] proxyClasses) {
        Set<Class<?>> superclasses = Sets.newLinkedHashSet();
        for (Class<?> proxyClass : proxyClasses) {
            if (!proxyClass.isInterface()) {
                superclasses.add(proxyClass);
            }
        }

        return superclasses.toArray(new Class[superclasses.size()]);
    }

    protected  Class<?>[] toInterfaces(Class<?>[] proxyClasses) {
        Set<Class<?>> interfaces = Sets.newLinkedHashSet();
        for (Class<?> proxyClass : proxyClasses) {
            if (proxyClass.isInterface()) {
                interfaces.add(proxyClass);
            }
        }

        interfaces.add(Serializable.class);
        return interfaces.toArray(new Class[interfaces.size()]);
    }


    protected   Class<?> getSuperclass(Class<?>[] proxyClasses) {
        final Class<?>[] superclasses = toNonInterfaces(proxyClasses);
        switch (superclasses.length) {
            case 0:
                return Object.class;
            case 1:
                Class<?> superclass = superclasses[0];
                if (Modifier.isFinal(superclass.getModifiers())) {
                    throw new PlatformProxyException(
                            "代理类不能继承" + superclass.getName()+",由于它被标记为final" );
                }
                if (!hasDefaultConstructor(superclass)) {
                    throw new PlatformProxyException(
                            "代理类不能继承 " + superclass.getName()
                                    + ", 由于构造函数不可见。");
                }
                return superclass;
            default:
                throw new PlatformProxyException("无法获取代理类的父类");
        }
    }
    protected  static <T, S extends T> T defaultIfNull(T object, S defaultValue) {
        return object == null ? defaultValue : object;
    }
}
