package com.eyesmart.testapplication.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 泛型，即“参数化类型”。解决数据类型安全问题
 * 分为 泛型类、泛型接口、泛型方法
 * Java 的泛型是伪泛型，这是因为类型擦除 ：Java 在编译期间，所有的泛型信息都会被擦掉，这也就是通常所说。
 */

public class MyGenericity extends Object{

    //通过<>进行传入泛型，后续即可使用

    //泛型类或接口
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


    public void test() {
        /**
         * 通配符适用于形参中，便于指定类型范围
         */
        Point<Integer> p = new Point<>();             //泛型类
        p.setX(1);
        out(1);                                //泛型方法

        out(p);                                       //通配符，形参是泛型类
    }

    public void out(Point<?> p) {                     //泛型的类型指定不能用Object，必须用 通配符?
    }

    public void out1(Point<? extends Number> p) {     //泛型的范围，extends上限，super下限
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
        c.getFields();                              //取得全部属性(包括父类及接口中)
        c.getDeclaredFields();                      //取得本类的全部属性
        c.getMethods();                             //取得全部方法

        Method equals = c.getMethod("equals", Object.class);//通过反射调用类中方法
        boolean b = (boolean) equals.invoke(stu, new Student());

        Field age = c.getDeclaredField("age");//通过反射操作属性
        age.setAccessible(true);                    //设置为可被外部访问
        age.set(stu, "18");
    }
}

