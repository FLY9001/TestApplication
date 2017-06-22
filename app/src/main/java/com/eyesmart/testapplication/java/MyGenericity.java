package com.eyesmart.testapplication.java;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * 泛型，解决数据类型安全问题
 */

public class MyGenericity {

    public void test() {
        Point<Integer> p = new Point<Integer>();      //声明或实例化时指定
        out(p);
    }

    public void out(Point<?> p) {                     //泛型的类型指定不能用Object，必须用 通配符?
    }

    public void out1(Point<? extends Number> p) {     //泛型的范围，上限
    }

    public void out2(Point<? super Number> p) {       //泛型的范围，下限
    }

    public <T extends Number> Point<T> out(T param) { //泛型方法返回泛型类型
        Point<T> point = new Point<>();
        point.setX(param);
        return point;
    }

    class Point<T extends Number> {
        private T x;
        private T y;

        public T getX() {
            return x;
        }

        @MyAnnotation(value = "abc", color = Color.BLUE)
        public void setX(T x) {
            this.x = x;
        }

        public T getY() {
            return y;
        }

        public void setY(T y) {
            this.y = y;
        }
    }

    /**
     * 注解
     */
    //@Override                         //覆写
    //@Deprecated                       //不建议使用
    //@SuppressWarnings("unChecked")    //抑制警告

    @Retention(value = RetentionPolicy.RUNTIME)     //执行时也会存在并起作用
    @Target(value = ElementType.METHOD)             //注解的应用位置，此为只能用在方法声明
    @Documented                                     //用于生成文档时被生成说明信息
    @Inherited                                      //父类的该注解可被子类继承
    public @interface MyAnnotation {
        public String[] value() default "aaa";      //默认值

        public Color color() default Color.RED;
    }

    {
        Class<Point> c = Point.class;
        Method toString = null;
        try {
            toString = c.getMethod("toString");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Annotation[] ans = toString.getAnnotations();//反射获得方法的所有（RUNTIME）注解
        if (toString.isAnnotationPresent(MyAnnotation.class)) {//若此注释存在
            MyAnnotation myAnnotation = toString.getAnnotation(MyAnnotation.class);
            myAnnotation.value();                    //获得此注解的属性
            myAnnotation.color();
        }
    }

    enum Color {
        RED, BLUE, GREEN;
    }
}

