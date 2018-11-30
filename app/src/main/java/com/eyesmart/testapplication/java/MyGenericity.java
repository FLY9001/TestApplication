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

    enum Color {
        RED, BLUE, GREEN;
    }
}

