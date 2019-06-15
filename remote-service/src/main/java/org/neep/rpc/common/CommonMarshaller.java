package org.neep.rpc.common;

import io.grpc.MethodDescriptor;

import java.io.InputStream;

/**
 * @Title CommonMarshaller
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-6 下午10:27
 */
public class CommonMarshaller implements  MethodDescriptor.Marshaller {
    @Override
    public InputStream stream(Object o) {
        return null;
    }

    @Override
    public Object parse(InputStream inputStream) {
        return null;
    }
}
