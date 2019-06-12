package org.neep.rpc.common;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RemoteServiceScannerRegistrar.class)
public @interface RemoteServiceScan {
    String[] basePackages() default {};
    String[] value() default {};
}
