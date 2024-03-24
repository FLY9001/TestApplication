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

//---基础语法-------------------------------------------------------------------------------------------------------------------

    fun test() {
        // variable，变量；value，值
        //可变变量定义：var 关键字
        //不可变变量定义：val 关键字，只能赋值一次的变量(类似Java中final修饰的变量)
        var a: Byte = 1
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
        //不允许赋值null，除非加?
        var age: String? = null         //类型后面加?表示可为null（当一个引用可能为null值时, 对应的类型声明必须明确地标记为可为null）
        var ages1 = age?.toInt()        //age为空，不做处理返回null
        var ages2 = age?.toInt() ?: -1  //age为空返回-1
        // 直接使用age会导致错误, 因为它们可能为 null
        if (age != null) {
            // 上面在进行过 null 值检查之后, age的类型在此处会被自动转换为非 null 变量
            print(age)
        }
        var ages = age!!.toInt()        //抛出空指针异常

        //类型检测is，相当于Java中instanceOf
        //自动类型转换
        var obj: Any = 0
        if (obj is String) {            //做过类型判断以后，obj会被系统自动转换为String类型
            print(age.length)
        }
        //这里的obj仍然是Any类型的引用
        if (obj !is String) {           //在这里还有一种方法，与Java中instanceOf不同，使用!is
            // XXX
        }
        //这里的obj是String类型
        obj as String                   //强转


        // 区间in
        var i: Int = 1
        for (i in 1..4) print(i) // 输出“1234”
        for (i in 4..1) print(i) // 什么都不输出
        if (i in 1..10) { // 等同于 1 <= i && i <= 10
            println(i)
        }
        // 使用 step 指定步长
        for (i in 1..4 step 2) print(i) // 输出“13”
        for (i in 4 downTo 1 step 2) print(i) // 输出“42”
        // 使用 until 函数排除结束元素
        for (i in 1 until 10) {   // i in [1, 10) 排除了 10
            println(i)
        }
    }


    //控制语句，条件&循环
    //if，when，for
    fun test2() {
        var a: Int = 0
        var b: Int = 0
        var ints: Array<Int> = arrayOf(0, 1, 2, 3)


        //if
        //可赋值
        var max = if (a > b) {
            a
        } else {
            b
        }
        //可作为表达式
        max = if (a > b) a else b


        //when，类似switch
        when (a) {
            0, 1 -> print("")
            in 2..10 -> print("")
            is Int -> print("")
            else -> print("")
        }
        //取代if-else链
        when {
            a == 0 -> print("")
            b == 1 -> print("")
            else -> print("")
        }

        //for,可遍历任何提供迭代器（iterator）的对象
        for (item: Int in ints) {
        }
        for (i in ints.indices) {
        }
        for ((i, item) in ints.withIndex()) {
        }

        //while，do...while
    }
}

