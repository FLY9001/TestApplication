package com.eyesmart.testapplication.kotlin

/**
 * 函数
 */
class MyFun {

    //关键字fun + 参数: 类型 + : 返回值类型
    fun sum(a: Int, b: Int): Int {   // Int 参数，返回值
        return a + b
    }
    // 无返回值时，类型为Unit，可省略
    // 表达式函数，=号取代返回值类型


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
//    fun main(args: Array<String>) {
//        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
//        println(sumLambda(1, 2))  // 输出 3
//    }
}

//扩展函数可以在已有类中添加新的方法，不会对原类做修改
//扩展函数是静态解析的，不是动态的类型决定的
//扩展函数和成员函数同名时，调用成员函数
fun User.Print() {
    print("我是扩展函数");
}

//扩展属性，必须val
val User.sex: Int
    get() = sex

fun main(args: Array<String>) {
    var user = User("", 18);
    user.Print()
}
