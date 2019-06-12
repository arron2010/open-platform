package org.neep.spring.ext.boot;

import org.neep.spring.ext.api.IBeanInitializer;
import org.neep.spring.ext.support.CglibSubclassingInstantiationStrategyEx;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ResourceLoader;

/**
 * @Title SpringApplicationEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create
 */
public class SpringApplicationEx extends SpringApplication {
    private IBeanInitializer beanInitializer;
    public SpringApplicationEx(Class<?>... primarySources) {
        super(primarySources);
    }

    public SpringApplicationEx(ResourceLoader resourceLoader, Class<?>... primarySources) {
        super(resourceLoader, primarySources);
    }

    /**
     *@title 注入新的Bean实例化策略
     *@description 为了实现根据对象的属性或特征值，动态增加对象行为，需要BeanFactory对象中，注入新的策略。
     * 此时，对象实例还未加入spring容器
     *@param  []
     *@return  org.springframework.context.ConfigurableApplicationContext
     *@author
     *@createDate
     */

    @Override
    protected ConfigurableApplicationContext createApplicationContext() {
        ConfigurableApplicationContext context= super.createApplicationContext();
        if (context instanceof  GenericApplicationContext){
            GenericApplicationContext genericApplicationContext = (GenericApplicationContext)context;
            AutowireCapableBeanFactory autowireCapableBeanFactory= genericApplicationContext.getDefaultListableBeanFactory();
            if (autowireCapableBeanFactory instanceof DefaultListableBeanFactory){
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)autowireCapableBeanFactory;
                defaultListableBeanFactory.setInstantiationStrategy(new CglibSubclassingInstantiationStrategyEx());
            }
        }
        return context;
    }

    public IBeanInitializer getBeanInitializer() {
        return beanInitializer;
    }

    public void setBeanInitializer(IBeanInitializer beanInitializer) {
        this.beanInitializer = beanInitializer;
    }
}
