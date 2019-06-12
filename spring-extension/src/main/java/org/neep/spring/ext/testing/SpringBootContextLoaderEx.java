package org.neep.spring.ext.testing;


import org.neep.spring.ext.boot.SpringApplicationEx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;

/**
 * @Title SpringBootContextLoaderEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 上午12:01
 */
public class SpringBootContextLoaderEx extends SpringBootContextLoader {
    public SpringBootContextLoaderEx() {
        super();
    }

    @Override
    protected SpringApplication getSpringApplication() {
        return new SpringApplicationEx(new Class[0]);
    }
}
