package com.eyesmart.testapplication.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by 1 on 2017/6/18 0018.
 */

public class MyEnum {
    /**枚举，一组固定的常量组成合法值的类型*/
    enum Color {
        RED, BLUE, GREEN;
    }

    @MyAnnotation(value = "abc", color = Color.BLUE)
    public void test() {
        for (Color c : Color.values()) {
            c.name();       //RED
            c.ordinal();    //序号：0
        }
    }

    /**
     * 注解
     * https://blog.csdn.net/briblue/article/details/73824058
     */
    //4个标准注解
    //@Override                         //覆写
    //@Deprecated                       //对不鼓励使用或者已过时的方法添加注解
    //@SuppressWarnings("unChecked")    //取消警告
    //@SafeVarargs                      //JDK 7新增，用来声明使用了可变长度参数的方法，其在与泛型类一起使用时不会出现类型安全问题。

    //5个元注解，定义注解的注解
    //@Target(value = ElementType.METHOD)   //注解所修饰的范围。ElementType.METHOD为适用于方法
    //@Inherited                            //表示注解可以被继承。
    //@Documented                           //表示这个注解应该被JavaDoc工具记录。用于生成文档时被生成说明信息
    //@Retention(RetentionPolicy.RUNTIME)
    //用来声明注解的保留策略。RUNTIME为运行时注解。当运行Java程序时，JVM也会保留该注解信息，可以通过反射获取该注解信息。
    //@Repeatable                         //JDK 8 新增，允许一个注解在同一声明类型（类、属性或方法）上多次使用。
    /**注解类*/
    public @interface MyAnnotation {
        String[] value() default "aaa";      //默认值

        Color color() default Color.RED;
    }

    /**反射获取注解*/
    {
        Class<MyEnum> c = MyEnum.class;
        Method test = null;
        try {
            test = c.getMethod("test");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (test.isAnnotationPresent(MyAnnotation.class)) {//若此注释存在
            MyAnnotation myAnnotation = test.getAnnotation(MyAnnotation.class);
            myAnnotation.value();                    //获得此注解的属性
            myAnnotation.color();
        }
        Annotation[] ans = test.getAnnotations();//反射获得方法的所有（RUNTIME）注解
    }
}
