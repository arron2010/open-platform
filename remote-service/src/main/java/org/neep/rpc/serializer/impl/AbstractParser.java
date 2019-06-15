package org.neep.rpc.serializer.impl;

import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.neep.rpc.serializer.api.IMessageParser;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title AbstractParser
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午12:42
 */
public abstract class AbstractParser implements IMessageParser {
    private Map<Class<?>,Schema> schemaMap = new HashMap<>();
    protected int bufferSize = 4096;

    protected  Schema getSchema(Class<?> clazz){
        if(this.schemaMap.containsKey(clazz)){
            return this.schemaMap.get(clazz);
        }
        Schema schema = RuntimeSchema.getSchema(clazz);
        schemaMap.put(clazz,schema);
        return schema;
    }
    protected abstract void  onSerialize(OutputStream out, Object message, io.protostuff.Schema schema, LinkedBuffer buffer) throws IOException;
    public  byte[] serialize(Object obj) throws IOException{
        ByteArrayOutputStream outputStream = null;
        try{
            final LinkedBuffer buffer = LinkedBuffer.allocate(bufferSize);
            outputStream = new ByteArrayOutputStream();
            this.onSerialize(outputStream,obj,
                    this.getSchema(obj.getClass()),
                    buffer);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        }finally {
            if (outputStream != null){
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    protected abstract void  onDeserialize(byte[] data, Object message, io.protostuff.Schema schema) throws IOException;

    public  Object deserialize(byte[] bytes,String className) throws IOException{
        Class clazz = null;
        try{
            clazz = Class.forName(className);
        }catch (ClassNotFoundException ex){
            throw  new IOException("反序列化时，"+className+"类未被加载");
        }
        Object obj =BeanUtils.instantiateClass(clazz);
        this.onDeserialize(bytes,obj,this.getSchema(clazz));
        return obj;
    }



    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
