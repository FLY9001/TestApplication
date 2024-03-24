package com.eyesmart.testapplication.kotlin

/**
 * 声明类，class关键字
 * 主构造器，constructor关键字，没有修饰时，可省略；如主构造函数所有参数都有默认值，编译器会生成一个无参构造函数
 * 初始化代码，init关键字
 * 次构造器，constructor关键字
 *
 * Any，所有类的超类，默认三个函数，equals()、hashCode()、toString()
 *
 * 类修饰符
 * final       // 不可继承类，类默认是final的
 * open        // 可继承类
 *
 * abstract    // 抽象类
 * interface   // 接口
 *
 * enum        // 枚举类
 * annotation  // 注解类
 *
 * inner       // 内部类
 * data        // 数据类
 * sealed      // 密封类
 *
 * 权限修饰符
 * private    // 仅在同一文件中可见
 * protected  // 同一个文件中或子类可见
 * public     // 所有调用的地方都可见
 *
 * internal   // 同一个模块中可见
 */
class MyClass private constructor(name: String) : Any() {
    //类的属性
    var name: String = ""
        get() {
            return field.toUpperCase()
        }
        private set
    var age = 18
    val myCs: MyClass = MyClass("", 18)

    //类的初始化代码
    init {
        println("Name is $name")
        //myCs.name访问时，自动调用get/set
        myCs.name = ""
    }

    //类的次构造器，调用主构造器，关键字this
    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

    //没有主构造器，次构造器必须初始化父类，关键字super
//    constructor(name: String, age: Int) : super(name) {
//        this.age = age
//    }

    //类的函数
    fun print() {

    }
}

//空类
class Empty

//抽象类，abstract关键字，无需open
abstract class Base {
    abstract fun f()
}

open class BaseImpl : Base() {

    override fun f() {
        TODO("Not yet implemented")
    }
}

//嵌套内部类，inner关键字
//可以访问外部类成员属性和成员函数。
class Outer() {
    var name: String = ""

    inner class Inner {
        fun test() {
            name
            var o = this@Outer
        }
    }
}

//接口，interface关键字
//
interface MyInterface {
    var name: String //属性, 接口中的属性是抽象的，不能初始化

    fun f()
    fun f1() {

    }
}

class Child(override var name: String) : MyInterface {
    override fun f() {
        TODO("Not yet implemented")
    }
}
class ChildB(override var name: String) : MyInterface {
    override fun f() {
        TODO("Not yet implemented")
    }
}



