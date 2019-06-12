package org.neep.rpc.common;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface RemoteService {
    String value() default "";
}
