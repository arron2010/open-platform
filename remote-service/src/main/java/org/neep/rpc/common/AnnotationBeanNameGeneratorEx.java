package org.neep.rpc.common;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * @Title AnnotationBeanNameGeneratorEx
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-13 下午3:22
 */
public class AnnotationBeanNameGeneratorEx extends AnnotationBeanNameGenerator {
    public AnnotationBeanNameGeneratorEx() {
        super();
    }
    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanName = super.buildDefaultBeanName(definition,registry);
        String newName=CharMatcher.is('I').trimLeadingFrom(beanName);
        newName = capitalFirst(newName);

        newName +="#proxy";
        return newName;
    }

    private String capitalFirst(String name) {
        if (!Strings.isNullOrEmpty(name)) {
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return "";
    }
}
