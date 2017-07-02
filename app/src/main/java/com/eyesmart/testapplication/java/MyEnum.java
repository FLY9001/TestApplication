package com.eyesmart.testapplication.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Created by 1 on 2017/6/18 0018.
 */

public class MyEnum {
    void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        //枚举类型
        for (Color c : Color.values()) {
            c.name();       //RED
            c.ordinal();    //序号：0
        }

        //反射机制
        Class<?> c = Class.forName("com.eyesmart.testapplication.java.Student");
        c = new Student().getClass();
        c = Student.class;                          //实例化Class对象的三种方式

        Student stu = (Student) c.newInstance();    //可以实例化对象，必须有无参构造器
        Constructor<?>[] cons = c.getConstructors();//取得全部构造器
        stu = (Student) cons[0].newInstance();      //找到可用的构造器实例化对象

        c.getInterfaces();                          //取得类实现的接口
        c.getSuperclass();                          //取得类的父类
        c.getMethods();                             //取得全部方法
        c.getFields();                              //取得全部属性(包括父类及接口中)
        c.getDeclaredFields();                      //取得本类的全部属性

        Method equals = c.getMethod("equals", Object.class);//通过反射调用类中方法
        boolean b = (boolean) equals.invoke(stu, new Student());

        Field age = c.getDeclaredField("age");      //通过反射操作属性
        age.setAccessible(true);                    //设置为可被外部访问
        age.set(stu, "18");
    }

    enum Color {
        RED, GREEN, BULE;
    }
}
