package org.neep.utils.tools;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import org.springframework.util.ClassUtils;

/**
 * @Title StringHelper
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company:
 * @Author
 * @Version 1.0.0
 * @Create 19-6-15 下午4:48
 */
public abstract class StringHelper {

    public static String capitalFirst(String name) {
        if (!Strings.isNullOrEmpty(name)) {
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return "";
    }

    public  static  String getBeanNameByClass(String className){
        String beanName = ClassUtils.getShortName(className);
        beanName=CharMatcher.is('I').trimLeadingFrom(beanName);
        beanName = StringHelper.capitalFirst(beanName);
        beanName +="#proxy";
        return beanName;
    }
    public static String removeInterfacePrefix(String str){
        String temp =CharMatcher.is('I').trimLeadingFrom(str);
        return temp;
    }
}
