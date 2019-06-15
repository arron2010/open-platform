package org.neep.rpc.msg.anno;

public @interface MessageType {
    String value() default "protobuf";
}
