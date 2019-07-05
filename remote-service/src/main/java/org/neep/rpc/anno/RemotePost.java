package org.neep.rpc.anno;


import net.devh.boot.grpc.server.service.GrpcService;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@GrpcService
public @interface RemotePost {
    /*
     配置服务被哪个中心实现
     */
    String value() default "";
    int weight() default  10;
}
