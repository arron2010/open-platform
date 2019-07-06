package org.neep.spring.ext.builder;

import org.neep.spring.ext.api.IBeanInitializer;
import org.neep.spring.ext.boot.SpringApplicationEx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Title SpringApplicationBuilderEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create
 */
public class SpringApplicationBuilderEx extends SpringApplicationBuilder {
    private IBeanInitializer beanInitializer;

    public SpringApplicationBuilderEx(Class<?>... sources) {
        super(sources);

    }

    protected SpringApplication createSpringApplication(Class... sources) {
        SpringApplicationEx springApplicationEx= new SpringApplicationEx(sources);
        return springApplicationEx;
    }

//    public IBeanInitializer getBeanInitializer() {
//        return beanInitializer;
//    }
//
//    public void setBeanInitializer(IBeanInitializer beanInitializer) {
//        this.beanInitializer = beanInitializer;
//    }
}
