package com.eyesmart.testapplication.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 泛型，解决数据类型安全问题
 */

public class MyGenericity {

    public void test() {
        /**
         * 泛型，即“参数化类型”。
         * 分为 泛型类、泛型接口、泛型方法
         * 通配符适用于形参中，便于指定类型范围
         */
        Point<Integer> p = new Point<Integer>();      //包括泛型的类，一般针对成员变量类型
        p.setX(1);
        out(1);                                //包括泛型的方法，一般针对形参类型

        out(p);                                       //通配符，形参是泛型类
    }

    public void out(Point<?> p) {                     //泛型的类型指定不能用Object，必须用 通配符?
    }

    public void out1(Point<? extends Number> p) {     //泛型的范围，上限
    }

    public void out2(Point<? super Number> p) {       //泛型的范围，下限
    }

    //泛型类或接口，最简单的只有T或别的字母
    class Point<T extends Number> {
        private T x;

        public T getX() {
            return x;
        }

        public void setX(T x) {
            this.x = x;
        }
    }

    //泛型方法
    public <T extends Number> boolean out(T param) { //泛型方法返回泛型类型
        return true;
    }


    /**
     * 反射机制
     */
    void test2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

        /**得到Class*/
        //实例化Class对象的三种方式
        Class<?> c = Class.forName("com.eyesmart.testapplication.java.Student");
        c = Student.class;
        c = new Student().getClass();

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

        Field age = c.getDeclaredField("age");//通过反射操作属性
        age.setAccessible(true);                    //设置为可被外部访问
        age.set(stu, "18");
    }
}

