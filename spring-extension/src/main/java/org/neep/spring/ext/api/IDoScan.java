package org.neep.spring.ext.api;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Set;

/**
 *@title
 *@description 自定义扫描包的接口
 *@param
 *@return  
 *@author
 *@createDate
 */

public interface IDoScan {

     Set<BeanDefinitionHolder> doScan(Set<BeanDefinitionHolder> beanDefinitionHolders, String... basePackages);
     TypeFilter[] createTypeFilters();
     boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition);
}
