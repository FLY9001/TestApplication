package com.eyesmart.testapplication.java;

/**
 * 泛型，解决数据类型安全问题
 */

public class MyGenericity {

    public void test() {
        //枚举类型
        for (Color c : Color.values()) {
            c.name();       //RED
            c.ordinal();    //序号：0
        }

        /**
         * 泛型，即“参数化类型”。
         * 分为 泛型类、泛型接口、泛型方法
         * 通配符适用于形参中，便于指定类型范围
         */
        Point<Integer> p = new Point<Integer>();      //声明或实例化时指定
        p.setX(1);
        out(p);
    }

    public void out(Point<?> p) {                     //泛型的类型指定不能用Object，必须用 通配符?
    }

    public void out1(Point<? extends Number> p) {     //泛型的范围，上限
    }

    public void out2(Point<? super Number> p) {       //泛型的范围，下限
    }

    //泛型方法
    public <T extends Number> Point<T> out(T param) { //泛型方法返回泛型类型
        Point<T> point = new Point<>();
        point.setX(param);
        return point;
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

    enum Color {
        RED, BLUE, GREEN;
    }
}

