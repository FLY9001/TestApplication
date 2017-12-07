package com.eyesmart.testapplication.java;

/**
 * 设计模式六大原则：
 * 1、单一职责   类要职责单一；一个类只负责一项职责
 * 2、里氏替换   不破坏继承关系；子类方法只新增不重写
 * 3、依赖倒置   面向接口编程（或抽象类）；抽象比细节更稳定
 * 4、接口隔离   接口要精简单一；接口中方法尽量少，可使用多个接口
 * 5、迪米特     最小知道原则；降低类与类之间的耦合，低耦合、高内聚
 * 6、开闭       对扩展开发，对修改关闭；用抽象构建框架，用实现扩展细节
 */

public class TestDesignPattern {
    void test() {
        //创建型
        Singleton.getInstance();    //单例模式

        //行为型

        //结构型

    }
}

//**********************************************************************************************************
//饿汉模式（java中饿汉更好）
class Singleton {
    private static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }
}

//懒汉模式（C++中一般使用懒汉）
class Singleton2 {
    private static Singleton2 singleton2;

    public static synchronized Singleton2 getInstance() {
        if (singleton2 == null) {
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}

//**********************************************************************************************************
interface IProduct {
    public void show();
}

class Product implements IProduct {

    @Override
    public void show() {

    }
}

interface IFactory {
    public IProduct creatProduct();
}

class Factory implements IFactory {
    @Override
    public Product creatProduct() {
        return new Product();
    }
}