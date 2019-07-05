package org.neep.rpc.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EtcdRegistry {
    String value() default "";
    int weight() default  10;
}

