package com.yangy.pay.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtils<T> {
    /**
     * @param t
     * @param args
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @Title:checkParam
     * @Description: (该方法用来校验对象及其属性是否为空)
     * @author
     * @修改时间：2017年11月9日 下午2:18:54
     * @修改内容：创建
     */
    public boolean checkParam(T t, String... args) {
        // 如果传入的对象为空，则直接抛出异常
        if (t == null) {
            return false;
        }
        Class<? extends Object> clazz = t.getClass();
        // 定义属性列表
        List<String> argsList = new ArrayList<String>();
        // 如果传入的属性名不为空，则将传入的属性名放入属性列表
        if (args != null && args.length > 0) {
            argsList = Arrays.asList(args);
        } else {// 如果传入的属性名为空，则将所有属性名放入属性列表
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                argsList.add(field.getName());
            }
        }
        // 获取该类自定义的方法数组
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 方法名
            String methodName = method.getName();
            // 获取方法对应的属性名
            String fieldName = "";
            if (methodName.length() >= 4) {
                fieldName = methodName.substring(3, 4).toLowerCase()
                        + methodName.substring(4);
                // 如果方法是“get方法”，并且属性列表中包含该方法对应的属性名
                try {
                    if (methodName.startsWith("get") && argsList.contains(fieldName)) {
                        // 如果为null，抛出异常
                        if (method.invoke(t) == null) {
                            return false;
                        }
                        // 如果该方法返回类型为String,返回结果为空字符串，抛出异常。
                        Class<?> returnType = method.getReturnType();
                        String returnTypeName = returnType.getSimpleName();
                        if (returnTypeName.equals("String") && "".equals(((String) (method.invoke(t))).trim())) {
                            return false;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public String[] getParams(Class<T> t) {
        Field[] fields = t.getDeclaredFields();
        String[] strs = new String[fields.length];
        List<String> list = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            strs[i] = name;
        }
        return strs;
    }
}