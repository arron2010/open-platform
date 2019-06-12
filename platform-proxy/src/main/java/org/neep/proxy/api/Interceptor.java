package org.neep.proxy.api;

import java.io.Serializable;


public interface Interceptor extends Serializable {

    Object intercept(Invocation invocation) throws Throwable;

}
