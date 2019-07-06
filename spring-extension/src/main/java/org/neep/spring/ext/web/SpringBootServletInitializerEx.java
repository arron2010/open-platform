package org.neep.spring.ext.web;

import org.neep.spring.ext.api.IBeanInitializer;
import org.neep.spring.ext.boot.SpringApplicationEx;
import org.neep.spring.ext.builder.SpringApplicationBuilderEx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Title SpringBootServletInitializerEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create
 */
public abstract  class SpringBootServletInitializerEx extends SpringBootServletInitializer {

    public SpringBootServletInitializerEx() {
        super();
    }
    protected SpringApplicationBuilder createSpringApplicationBuilder() {
        SpringApplicationBuilderEx springApplicationBuilderEx =  new SpringApplicationBuilderEx(new Class[0]);
        SpringApplication application = springApplicationBuilderEx.application();
        if (application instanceof  SpringApplicationEx){
            SpringApplicationEx applicationEx = (SpringApplicationEx)application;
            applicationEx.setBeanInitializer(this.createBeanInitializer());
        }

        return springApplicationBuilderEx;
    }


    /**
     *@title
     *@description 创建bean初始化接口
     *@param
     *@return  org.neep.spring.ext.api.IBeanInitializer
     *@author
     *@createDate
     */

    protected abstract IBeanInitializer createBeanInitializer();
}
