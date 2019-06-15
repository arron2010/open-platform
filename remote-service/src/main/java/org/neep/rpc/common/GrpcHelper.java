package org.neep.rpc.common;

import com.google.common.base.Splitter;
import com.google.protobuf.ByteString;
import io.grpc.MethodDescriptor;
import org.neep.rpc.msg.entity.RpcMsg;
import org.neep.rpc.serializer.SerializerFactory;
import org.neep.rpc.serializer.api.IMessageParser;
import org.neep.utils.exceptions.RemoteServiceException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static io.grpc.MethodDescriptor.generateFullMethodName;

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
    public static MethodDescriptor getMethodDescriptor(Class<?> service,Method method){

        String serviceName= getServiceName(service);

        MethodDescriptor methodDescriptor =MethodDescriptor.newBuilder().setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                .setFullMethodName(generateFullMethodName(
                        serviceName, method.getName()))
                .setSampledToLocalTracing(true)
                .setRequestMarshaller(new CommonMarshaller())
                .setResponseMarshaller(new CommonMarshaller())
                .build();
        return methodDescriptor;
    }

    public  static String getServiceName(Class<?> service){
        String className = service.getName();
        List<String> list =Splitter.on(".").splitToList(className);
        String serviceName= list.get(list.size()-2) +"."+list.get(list.size()-1);
        return serviceName;
    }

    public static RpcMsg.Message getGrpcMessage(String msgType,Object obj){
        IMessageParser parser = SerializerFactory.getInstance().getParser(msgType);
        try{
            byte[] buffer =parser.serialize(obj);
            RpcMsg.Message message = RpcMsg.Message.newBuilder()
                    .setMsgType(msgType)
                    .setResultText(obj.getClass().getName())
                    .setContent( ByteString.copyFrom(buffer))
                    .build();
            return message;
        }
        catch (IOException ex){
            throw  new RemoteServiceException("消息类型："+msgType+" ;对象："+obj.getClass().getName()+";序列化异常。原因："+ex.getMessage(),ex);
        }
    }

    public static Object restoreFromGrpcMessage(RpcMsg.Message msg){
        IMessageParser parser = SerializerFactory.getInstance().getParser(msg.getMsgType());
        try{
            Object obj = parser.deserialize(msg.getContent().toByteArray(),msg.getResultText());
            return obj;
        }catch (IOException ex){
            throw  new RemoteServiceException("消息类型："+msg.getMsgType()+" ;对象："+msg.getResultText()+";序列化异常。原因："+ex.getMessage(),ex);
        }
    }

}
