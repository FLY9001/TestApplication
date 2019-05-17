package com.eyesmart.testapplication.java;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * Created by 1 on 2017/6/18 0018.
 */

public class MyReflect {

    @MyAnnotation(value = "abc", color = MyGenericity.Color.BLUE)
    void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

        //反射机制
        /**得到Class*/
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

        Field age = c.getDeclaredField("age");   //通过反射操作属性
        age.setAccessible(true);                    //设置为可被外部访问
        age.set(stu, "18");
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
    @Target(value = ElementType.METHOD)   //注解所修饰的范围。ElementType.METHOD为适用于方法
    @Inherited                            //表示注解可以被继承。
    @Documented                           //表示这个注解应该被JavaDoc工具记录。用于生成文档时被生成说明信息
    @Retention(RetentionPolicy.RUNTIME)   //用来声明注解的保留策略。RUNTIME为运行时注解。当运行Java程序时，JVM也会保留该注解信息，可以通过反射获取该注解信息。
    //@Repeatable                         //JDK 8 新增，允许一个注解在同一声明类型（类、属性或方法）上多次使用。
    public @interface MyAnnotation {
        public String[] value() default "aaa";      //默认值

        public MyGenericity.Color color() default MyGenericity.Color.RED;
    }

    {
        Class<MyReflect> c = MyReflect.class;
        Method test = null;
        try {
            test = c.getMethod("test");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Annotation[] ans = test.getAnnotations();//反射获得方法的所有（RUNTIME）注解
        if (test.isAnnotationPresent(MyAnnotation.class)) {//若此注释存在
            MyAnnotation myAnnotation = test.getAnnotation(MyAnnotation.class);
            myAnnotation.value();                    //获得此注解的属性
            myAnnotation.color();
        }
    }
}
