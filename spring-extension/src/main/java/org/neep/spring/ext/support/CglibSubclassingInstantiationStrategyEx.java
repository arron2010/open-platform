package org.neep.spring.ext.support;

import org.neep.spring.ext.api.IBeanInitializer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @Title CglibSubclassingInstantiationStrategyEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create
 */
public class CglibSubclassingInstantiationStrategyEx extends CglibSubclassingInstantiationStrategy {
    private IBeanInitializer beanInitializer;

    public CglibSubclassingInstantiationStrategyEx() {
        super();
    }

    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        Object src = super.instantiate(bd, beanName, owner);
        if (this.beanInitializer != null) {
            //检查实现类的接口是否带有RemoteService
            boolean allowed = false;
            if (!bd.getBeanClass().isInterface()) {
                allowed = this.beanInitializer.allowInstantiated(bd, beanName, owner, src);
                if (allowed) {
                    return this.beanInitializer.instantiateBean(bd, beanName, owner, src);
                }
                //found = ReflectionHelper.findAnnotationUp(bd.getBeanClass(),RemoteService.class);
            }
        }
        return src;
    }

    public void setBeanInitializer(IBeanInitializer beanInitializer) {
        this.beanInitializer = beanInitializer;
    }
}
