package com.eyesmart.testapplication.kotlin

import android.app.Application
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener

/**
 * 对象声明，object关键字
 * 访问时延迟初始化
 *
 * 对象表达式，使用时立即执行
 * 可以实现匿名内部类
 * 1、匿名内部类，实现接口或抽象类
 * 2、无需类，直接创建对象
 */
class MyObject {
    //对象Site是单例，Object.Site
    object Site{

    }

    //伴生对象，companion object关键字，外部类可直接访问，就像静态一样，只能有一个伴生对象
    // Object.create()
    // 只能有一个伴生对象，可通过Object.companion访问
    companion object Factory {
        fun create(): MyObject = MyObject()
    }

    fun main(args: Array<String>) {
        View(Application()).setOnTouchListener(object : OnTouchListener, OnClickListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        });

        //用作只在本地和私有作用域中声明的类型
        val site = object {
            var name: String = "菜鸟教程"
            var url: String = "www.runoob.com"
        }
    }
}