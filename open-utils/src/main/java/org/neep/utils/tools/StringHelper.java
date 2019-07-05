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

    /**
     *@title
     *@description 解析GRPC服务名称
     *@param
     *@return  java.lang.String
     *@author  肖鹏
     *@createDate  19-2-21 上午9:56
     */
    public  static String parseServiceName(String name) throws Exception{
        String[] fieldInfo = name.toString().split("\\.");
        if (fieldInfo.length < 2){
            throw new RuntimeException("服务名称格式不规范");
        }
        return fieldInfo[1];
    }

    /**
     *@title
     *@description 对重点文本用“【】”包裹
     *@param
     *@return  java.lang.String
     *@author  肖鹏
     *@createDate  19-7-1 下午1:30
     */
    
    public  static String  wrapText(String text){
        return "【"+text +"】";
    }

    static int indexOf(CharSequence cs, CharSequence searchChar, int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }


    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        if (seq != null && searchSeq != null) {
            return indexOf(seq, searchSeq, 0) >= 0;
        } else {
            return false;
        }
    }
    public static boolean equals(String cs1, String cs2){
        if (cs1 == null && cs2 == null){
            return true;
        }
        if (cs1 == null || cs2 == null ){
            return false;
        }
        return cs1.equals(cs2);
    }
}
