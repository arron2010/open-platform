package org.neep.spring.ext.annotation;

import com.google.common.base.Preconditions;
import org.neep.spring.ext.api.IDoScan;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

/**
 * @Title ClassPathBeanDefinitionScannerEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-10 上午9:02
 */
public class ClassPathBeanDefinitionScannerEx extends ClassPathBeanDefinitionScanner {

    private IDoScan scanner;

    public ClassPathBeanDefinitionScannerEx(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public ClassPathBeanDefinitionScannerEx(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public ClassPathBeanDefinitionScannerEx(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
        super(registry, useDefaultFilters, environment);
    }

    public ClassPathBeanDefinitionScannerEx(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Preconditions.checkState(this.scanner != null,"IDoScan对象不能为空");
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        beanDefinitionHolders= this.scanner.doScan(beanDefinitionHolders,basePackages);
        return beanDefinitionHolders;
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return this.scanner.isCandidateComponent(beanDefinition);
    }
    public void registerFilters(){

        Preconditions.checkState(this.scanner != null,"IDoScan对象不能为空");
        TypeFilter[] typeFilters = this.scanner.createTypeFilters();
        if (typeFilters != null && typeFilters.length >0){
            for (int i =0;i < typeFilters.length;i++){
                this.addIncludeFilter(typeFilters[i]);
            }
       }else{
            //如果类型过滤器为空，默认接受所有类
            addIncludeFilter(new TypeFilter() {
                @Override
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return true;
                }
            });
        }
    }
    public void setScanner(IDoScan scanner) {
        this.scanner = scanner;
    }

}
