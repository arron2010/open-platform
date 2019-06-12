package org.neep.rpc.common;

import org.neep.spring.ext.annotation.ClassPathBeanDefinitionScannerEx;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title RemoteServiceScannerRegistrar
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-9 下午10:43
 */
public class RemoteServiceScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RemoteServiceScan.class.getName()));


        ClassPathBeanDefinitionScannerEx classPathBeanDefinitionScannerEx = new ClassPathBeanDefinitionScannerEx(beanDefinitionRegistry);
        DoScanImpl scanner = new DoScanImpl();
        classPathBeanDefinitionScannerEx.setScanner(scanner);

        classPathBeanDefinitionScannerEx.registerFilters();

        String[] basePackages = this.getBasePackages(annoAttrs);
        classPathBeanDefinitionScannerEx.doScan(basePackages);


        System.out.println("registerBeanDefinitions");
    }

    private String[] getBasePackages( AnnotationAttributes annoAttrs ){
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        return StringUtils.toStringArray(basePackages);
    }
}
