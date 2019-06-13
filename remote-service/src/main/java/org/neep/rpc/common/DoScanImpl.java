package org.neep.rpc.common;

import org.neep.rpc.client.RemoteServiceFactoryBean;
import org.neep.rpc.client.RemoteServiceRegistry;
import org.neep.spring.ext.api.IDoScan;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.Set;

/**
 * @Title DoScanImpl
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-10 上午10:43
 */
public class DoScanImpl implements IDoScan {

    private RemoteServiceFactoryBean<?> serviceFactoryBean;
    private RemoteServiceRegistry remoteServiceRegistry;

    public DoScanImpl() {
        remoteServiceRegistry = new RemoteServiceRegistry();
        serviceFactoryBean = new RemoteServiceFactoryBean<Object>();
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(Set<BeanDefinitionHolder> beanDefinitionHolders, String... basePackages) {
        GenericBeanDefinition definition;

        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(this.serviceFactoryBean.getClass());

            definition.setPrimary(true);
            definition.getPropertyValues().add("remoteServiceRegistry", remoteServiceRegistry);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitionHolders;
    }

    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition){
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
    @Override
    public TypeFilter[] createTypeFilters() {
        return new TypeFilter[]{new AnnotationTypeFilter(RemoteService.class)};
    }


}
