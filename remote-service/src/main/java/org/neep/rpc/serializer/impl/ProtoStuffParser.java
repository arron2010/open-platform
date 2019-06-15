package org.neep.rpc.serializer.impl;

import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import org.neep.rpc.serializer.api.IMessageParser;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title ProtoStuffParser
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 上午10:49
 */
public class ProtoStuffParser extends AbstractParser{
    @Override
    protected void onSerialize(OutputStream out, Object message, Schema schema, LinkedBuffer buffer) throws IOException{

    }

    @Override
    protected void onDeserialize(byte[] data, Object message, Schema schema) throws IOException{

    }
}
