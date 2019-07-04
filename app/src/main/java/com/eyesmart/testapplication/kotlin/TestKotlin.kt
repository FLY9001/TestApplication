//文件后缀名为.kt
//包结构不必和类目录名一致
package com.eyesmart.testapplication.kotlin

/*
以下的包会默认导入到每个Kotlin文件中：
kotlin.*
kotlin.annotation.*
kotlin.collections.*
kotlin.comparisons.*
kotlin.io.*
kotlin.ranges.*
kotlin.sequences.*
kotlin.text.*
 */
class TestKotlin {

//---函数-------------------------------------------------------------------------------------------------------------------

    //函数定义关键字 fun，参数格式为：参数 : 类型
    fun sum(a: Int, b: Int): Int {   // Int 参数，返回值
        return a + b
    }

    //表达式函数，返回类型自动推断：
    fun sum0(a: Int, b: Int) = a + b

    public fun sum1(a: Int, b: Int): Int = a + b   //public方法则必须明确写出返回类型

    //无返回值的函数 Unit(类似Java中的void)，可以省略
    fun printSum(a: Int, b: Int): Unit {
        print(a + b)
    }

    //函数的可变长参数关键字 vararg
    //vars(1, 2, 3, 4, 5)  // 输出12345
    fun vars(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

    //TODO lambda表达式使用实例
    fun main(args: Array<String>) {
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        println(sumLambda(1, 2))  // 输出 3
    }

//---变量-------------------------------------------------------------------------------------------------------------------

    //可变变量定义：var 关键字
    //不可变变量定义：val 关键字，只能赋值一次的变量(类似Java中final修饰的变量)
    fun test() {
        var a: Int = 1
        val b = 1       //系统自动推断类型为Int
        var c: Int      //不初始化必须指明类型
        c = 1


        //字符串模板，$表示一个变量名或者变量值
        //$varName表示变量值
        //${varName.fun()}表示变量的方法返回值:

        // 模板中的简单名称：
        var s1 = "a is $a"
        a = 2
        // 模板中的任意表达式：
        var s2 = "${s1.replace("is", "was")}, but now is $a"


        //NULL检查机制
        var age: String? = "23"         //类型后面加?表示可为空，当一个引用可能为 null 值时, 对应的类型声明必须明确地标记为可为 null。
        var ages = age!!.toInt()        //抛出空指针异常
        var ages1 = age?.toInt()        //age为空，不做处理返回null
        var ages2 = age?.toInt() ?: -1  //age为空返回-1

    }

    /*块注释/*可嵌套*/*/


}