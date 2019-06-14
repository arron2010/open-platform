package org.neep.rpc.common;

import io.grpc.MethodDescriptor;

import java.lang.reflect.Method;

/**
 * @Title GrpcHelper
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-14 下午8:27
 */
public abstract class GrpcHelper {
    public static MethodDescriptor getMethodDescriptor(Method method){
        return null;
    }

}
