package com.eyesmart.testapplication.project;

import com.eyesmart.testapplication.R;
import com.eyesmart.testapplication.ui.TestView;

/**
 * 资源文件，可通过R类进行访问
 */

public class Resource {
    void test() {
        values();       //简单值xml，数组、颜色、数值、字段、风格

        anim();         //动画文件
        animator();     //属性动画文件
        
        drawable();     //图片文件、drawable的xml
        mipmap();       //图标

        layout();       //布局文件
        menu();         //菜单定义文件

        raw();          //原生多媒体文件
        xml();          //原生xml

        //屏幕适配
        //国际化
    }

    void values() {
        int[] values = {
                R.array.reboot_item,
                R.color.colorAccent,
                R.dimen.fab_margin,
                R.string.app_name,
                R.styleable.TestView_name,
                R.style.AppTheme,
        };

        //R.styleable.TestView_name,
        new TestView(null);
    }

    void anim() {
        int[] anim = {
                R.anim.tween_animation, //补间动画
                R.anim.layout_animation,//Layout动画
        };
    }

    void animator() {
        int[] animator = {
                R.animator.property_animator,
        };
    }

    public static void drawable() {
        int[] drawable = {
                R.drawable.xy,                  //普通图片
                R.drawable.message_left,        //9patch图片

                //xml自定义
                R.drawable.bitmap_drawable,     //bitmap图片，可设置更多效果
                R.drawable.layer_drawable,      //layer 图片，可设置SeekBar的外观
                R.drawable.levellist_drawable,  //level 图片
                R.drawable.shape_drawable,      //shape 图形
                R.drawable.select_drawable,     //select图片

                R.drawable.frame_animation,     //帧动画
        };
    }

    void layout() {
        int[] layout = {
                R.layout.activity_main,
        };
    }

    void menu() {
        int[] menu = {
                R.menu.menu_main,
        };
    }

    void mipmap() {
        int[] mipmap = {
                R.mipmap.ic_launcher,
        };
    }

    void raw() {
        int[] raw = {
                R.raw.test,
        };
    }

    void xml() {
        int[] xml = {
                R.xml.books,
        };
    }
}
