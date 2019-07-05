package org.neep.utils.reflect;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Title ReflectionHelper
 * @Description
 * @Copyright: 版权所有 (c) 2018 - 2019
 * @Company: gdjt
 * @Author root
 * @Version 1.0.0
 * @Create 19-6-8 下午6:35
 */
public abstract class ReflectionHelper {

    public  static boolean existMethod(Method method,Class<?> ... types){
        for (int i =0;i < types.length;i++){
            Method found = ReflectionUtils.findMethod(types[i],method.getName(),method.getParameterTypes());
            if (found != null){
                return true;
            }else {
                continue;
            }
        }
        return  false;
    }

    public static boolean findAnnotationUp(Class<?> clazz, Class<? extends Annotation> target){
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i=0;i < interfaces.length;i++){
            Annotation annotation= interfaces[i].getAnnotation(target);
            if (annotation != null){
                return  true;
            }else{
                continue;
            }
        }
        return  false;
    }
    public static Class<?> findInterfaceByAnno(Class<?> clazz, Class<? extends Annotation> target){
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i=0;i < interfaces.length;i++){
            Annotation annotation= interfaces[i].getAnnotation(target);
            if (annotation != null){
                return  interfaces[i];
            }else{
                continue;
            }
        }
        return  null;
    }

    /**
     *@title
     *@description 获取父类的嵌套类的上级类静态字段值
     *@param
     *@return  java.lang.Object
     *@author  肖鹏
     *@createDate  19-2-21 上午9:54
     */

    public static Object findStaticFieldValue(Object bean,String fieldName) {
        Class clazz = bean.getClass().getSuperclass().getEnclosingClass();
        return ReflectionHelper.findStaticFieldValue(clazz,fieldName);
    }

    /**
     *@title
     *@description 获取类的静态字段值
     *@param
     *@return  java.lang.Object
     *@author  肖鹏
     *@createDate  19-2-21 上午9:53
     */

    public static Object findStaticFieldValue(Class clazz,String fieldName) {
        try{
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                if (field.getName().equals(fieldName)){
                    Object object = field.get(clazz);
                    return object;
                }
            }
            return  null;
        }
        catch (Exception ex){
            return null;
        }
    }

    public static boolean isHashCode(Method method) {
        return "hashCode".equals(method.getName()) && Integer.TYPE.equals(method.getReturnType())
                && method.getParameterTypes().length == 0;
    }

    public static boolean isEqualsMethod(Method method) {
        return "equals".equals(method.getName()) && Boolean.TYPE.equals(method.getReturnType())
                && method.getParameterTypes().length == 1 && Object.class.equals(method.getParameterTypes()[0]);
    }
}
