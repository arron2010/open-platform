package org.neep.spring.ext.api;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 *@title
 *@description 自定义spring bean初始化接口
 *@param
 *@return
 *@author
 *@createDate
 */

public interface IBeanInitializer {

    Object instantiateBean(BeanDefinition bd, String beanName, BeanFactory owner, Object src);
    boolean allowInstantiated(BeanDefinition bd, String beanName, BeanFactory owner, Object src);
}
