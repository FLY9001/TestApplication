package com.eyesmart.testapplication.java;

import android.animation.ValueAnimator;
import android.support.v7.app.AlertDialog;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eyesmart.testapplication.TestApplication;

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
    void test() throws CloneNotSupportedException {
        //创建型
        Singleton.getInstance();                        //单例模式
        ProductFactory.createProduct(ProductA.class);   //工厂模式、抽象工厂
        new TestBuilder().build();                      //Builder模式
        new TestClone().clone();                        //原型模式
        //行为型
        new TestStrateg().test();                       //策略模式
        new TestState().test();                         //状态模式
        new TestChain().test();                         //责任链模式


        //结构型

    }
}

//**********************************************************************************************************

/**
 * 单例模式
 * 定义、场景：确保某个类只有一个实例，避免产生多个对象消耗过多的资源
 * <p>
 * 优点：
 * （1）由于单例模式在内存中只有一个实例，减少了内存开支，特别是一个对象需要频繁的创建、销毁时，而且创建或销毁时性能又无法优化，单例模式的优势就非常明显。
 * （2）单例模式可以避免对资源的多重占用，例如一个文件操作，由于只有一个实例存在内存中，避免对同一资源文件的同时操作。
 * （3）单例模式可以在系统设置全局的访问点，优化和共享资源访问，例如，可以设计一个单例类，负责所有数据表的映射处理。
 * 缺点：
 * （1）单例模式一般没有接口，扩展很困难，若要扩展，只能修改代码来实现。
 * （2）单例对象如果持有Context，那么很容易引发内存泄露。此时需要注意传递给单例对象的Context最好是Application Context。
 * <p>
 * Android源码：
 * Context获取系统级别的服务，如WindowsManagerService、ActivityManagerService等，和LayoutInflater
 */
//饿汉模式（java中饿汉更好）
class Singleton {
    private static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }
}

//懒汉模式（C++中一般使用懒汉）
//getInstance时需要进行同步，反应稍慢，造成不必要的开销，所以这种不建议使用
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

/**
 * 工厂模式
 * 定义：定义一个创建对象的接口，让子类去决定实现化哪个类
 * 场景：生成复杂对象时，用工厂去创建产品
 * <p>
 * 优点：
 * 1.封装了创建过程，降低了对象之间的耦合。高层模块只需要知道产品的抽象类，其他的实现都不需要关心。
 * 2.良好的封装性，代码结构清晰。扩展性好。
 * 缺点：
 * 类结构较复杂，在比较简单时，不需要使用工厂模式。
 * <p>
 * Android源码：
 * 1.Activity的onCreate可看出工厂方法，因为setContentView
 * 2.ArrayList和HashSet
 */
class ProductFactory {
    /**
     * 抽象工厂方法
     * 具体由子类实现
     *
     * @param clz 产品对象类类型
     * @return 具体的产品对象
     */
    public static <T extends Product> T createProduct(Class<T> clz) {
        Product product = null;
        try {
            product = (Product) Class.forName(clz.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) product;
    }
}

abstract class Product {
    /**
     * 产品类的抽象方法
     * 由具体的产品类去实现
     */
    public abstract void method();
}

class ProductA extends Product {

    @Override
    public void method() {
        System.out.println("我是产品A");
    }
}

class ProductB extends Product {

    @Override
    public void method() {
        System.out.println("我是产品B");
    }
}

//**********************************************************************************************************

/**
 * 建造者模式
 * 定义：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 场景：
 * （1）相同的方法，不同的执行顺序，产生不同的事件结果时。
 * （2）多个部件或零件，都可以装配到一个对象中，但是产生的运行结果又不相同时。
 * （3）产品类非常复杂，或者产品类中的调用顺序不同产生了不同的作用，这个使用建造者模式非常适合。
 * （4）当初始化一个对象特别复杂时，如参数多，且很多参数有默认值。
 * <p>
 * 优点：
 * （1）良好的封装性，使用建造者模式可以使客户端不必知道产品内部组成细节。
 * （2）建造者独立，容易扩展。
 * 缺点：
 * （1）会产生多余的Builder对象及Director对象，消耗内存。
 * <p>
 * Android源码：
 * AlertDialog.Builder
 */
class TestBuilder {
    void build() {
        new AlertDialog.Builder(TestApplication.getContext()).show();
    }
}

//**********************************************************************************************************

/**
 * 原型模式
 * 定义：用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。被复制的实例就是“原型”，这个原型是可定制的。
 * 场景：
 * （1）类初始化需要消化非常多的资源，这个资源包括数据、硬件资源等，通过原型拷贝避免这些消耗。
 * （2）通过new产生的一个对象需要非常繁琐的数据准备或者权限，这时可以使用原型模式。
 * （3）一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用，即保护性拷贝。
 * <p>
 * 优点：
 * （1）原型模式是在内存中二进制流的拷贝，要比直接new一个对象性能好很多，特别是要在一个循环体内产生大量对象时，原型模式可能更好的体现其优点。
 * （2）还有一个重要的用途就是保护性拷贝，也就是对某个对象对外可能是只读的，为了防止外部对这个只读对象的修改，通常可以通过返回一个对象拷贝的形式实现只读的限制。
 * 缺点：
 * （1）这既是它的优点也是缺点，直接在内存中拷贝，构造函数是不会执行的，在实际开发中应该注意这个潜在问题。优点是减少了约束，缺点也是减少了约束，需要大家在实际应用时考虑。
 * （2）通过实现Cloneable接口的原型模式在调用clone函数构造实例时并不一定比通过new操作速度快，只有当通过new构造对象较为耗时或者说成本较高时，通过clone方法才能够获得效率上的提升。
 * <p>
 * Android源码：
 * Intent
 */
class TestClone {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

//**********************************************************************************************************

/**
 * 策略模式
 * 定义：策略模式定义了一系列的算法，并将每一个算法封装起来，而且使他们还可以相互替换。策略模式让算法独立于使用它的客户而独立变化。
 * 场景：
 * 1.针对同一类型问题的多种处理方式，仅仅是具体行为有差别时。
 * 2.需要安全地封装多种同一类型的操作时。
 * 3.出现同一抽象类有多个子类，而又需要使用if-else或者switch-case来选择具体子类时。
 * <p>
 * 优点：
 * 1.结构清晰明了、使用简单直观。
 * 2.耦合度相对而言较低，扩展方便。
 * 3.操作封装也更为彻底，数据更为安全。
 * 缺点：
 * 1.随着策略的增加，子类也会变得繁多。
 * <p>
 * Android源码：
 * 1.时间插值器（TimeInterpolator）
 */
class TestStrateg {
    void test() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());  //时间插值器，可自定义
        animator.addListener(null);//监听
        animator.start();
    }
}

//**********************************************************************************************************

/**
 * 状态模式
 * 定义：状态模式中的行为是由状态来决定，不同的状态下有不同的行为。当一个对象的内在状态改变时允许改变其行为，这个对象看起来像是改变了其类。
 * 场景：
 * 1.一个对象的行为取决于它的状态，并且它必须在运行时根据状态改变它的行为。
 * 2.代码中包含大量与对象状态有关的条件语句，例如，一个操作中含有大量的多分支语句，且这些分支依赖于该对象的状态。
 * <p>
 * 优点
 * 将所有与一个特定的状态相关的行为都放入一个状态对象中，它提供了一个更好的方法来组织与特定状态相关的代码，将繁琐的状态判断转换成结构清晰的状态类族，在避免代码膨胀的同时也保证了可扩展性与可维护性。
 * 缺点
 * 状态模式的使用必然会增加系统类和对象的个数。
 * <p>
 * Android源码：
 * 1.登录系统，根据用户是否登录，判断事件的处理方式。
 * 2.Wi-Fi管理，在不同的状态下，WiFi的扫描请求处理不一。
 */

class TestState {
    void test() {
    }
}

//**********************************************************************************************************

/**
 * 责任链模式
 * 定义：使多个对象都有机会处理请求，从而避免了请求的发送者和接受者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有对象处理它为止。
 * 场景：
 * 1.多个对象可以处理同一请求，但具体由哪个对象处理则在运行时动态决定。
 * 2.在请求处理者不明确的情况下向多个对象中的一个提交请求。
 * 3.需要动态指定一组对象处理请求。
 * <p>
 * 优点
 * 可以对请求者和处理者的关系解耦，提高代码的灵活性。
 * 缺点
 * 每次都需要对链中请求处理者遍历，如果处理者太多那么遍历必定会影响性能，特别是在一些递归调用者中，要慎用。
 * <p>
 * Android源码：
 * View事件的分发处理
 */
class TestChain {
    void test() {
        //公司报销费用
    }
}