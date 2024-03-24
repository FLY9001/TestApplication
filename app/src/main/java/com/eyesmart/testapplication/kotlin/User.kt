package com.eyesmart.testapplication.kotlin

/**
 * 数据类，
 * 自动生成函数，根据主构造中属性自动生成数据类相关函数：equals()、hashCode()、toString()、copy()、componentN()(组件函数，用于解构声明，把一个对象 解构 成很多变量)
 * 要求
 * 1、data关键字
 * 2、主构造至少一个参数，且用 var 或 val修饰
 * 3、大括号可以省略，
 * 4、不能声明为abstract、open、sealed、inner
 *
 *
 *
 */
data class User(val name: String, val age: Int) {
//    lateinit var a: Pair<String, String>;
//    lateinit var b: Triple<String, String, String>;
}

/**
 * 枚举
 */

enum class Color{
    RED,BLUE
}
class Main(){
    fun main(){
        println(Color.values())             //Array<Color>
        println(Color.valueOf("RED")) //RED
    }
}

enum class Color1(val rgb:Int){
    RED(0xFF0000),BLUE(0x00FF00)
}

enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}