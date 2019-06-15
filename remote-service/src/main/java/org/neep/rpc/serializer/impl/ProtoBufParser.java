package org.neep.rpc.serializer.impl;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title ProtoBufParser
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 上午9:27
 */
public class ProtoBufParser extends AbstractParser {
    @Override
    protected void onSerialize(OutputStream out, Object message, Schema schema, LinkedBuffer buffer) throws IOException{
        ProtobufIOUtil.writeTo(out, message, schema, buffer);
    }

    @Override
    protected void onDeserialize(byte[] data, Object message, Schema schema) throws IOException{
        ProtobufIOUtil.mergeFrom(data, message, schema);
    }
}
