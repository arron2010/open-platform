package org.neep.rpc.serializer;

import org.neep.rpc.msg.entity.MsgTypeConstants;
import org.neep.rpc.serializer.api.IMessageParser;
import org.neep.rpc.serializer.impl.ProtoBufParser;
import org.neep.rpc.serializer.impl.ProtoStuffParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title SerializerFactory
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 上午9:52
 */
public class SerializerFactory {

    private static  SerializerFactory instance = null;
    private final Map<String,IMessageParser> parserMap;

    private SerializerFactory(){
        parserMap= new HashMap<>();
    }
    public static SerializerFactory getInstance(){
        if (instance == null){
            synchronized (SerializerFactory.class){
                if (instance == null){
                    instance = new SerializerFactory();
                }
            }
        }
        return instance;
    }

    public  synchronized IMessageParser getParser(String msgType){
        if (parserMap.containsKey(msgType)){
            return parserMap.get(msgType);
        }
        IMessageParser parser = null;
        if (msgType.equals(MsgTypeConstants.PROTOSTUFF)){
            parser = new ProtoStuffParser();
        }
        if (parser == null){
            parser = new ProtoBufParser();
        }
        parserMap.put(msgType,parser);
        return parser;
    }
}
