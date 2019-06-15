package org.neep.rpc.serializer.api;

/**
 * @Title ISerializer
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 上午9:21
 */
public interface IMessageParser {
    byte[] serialize(Object obj) throws java.io.IOException;
    Object deserialize(byte[] bytes,String className) throws java.io.IOException;
}
