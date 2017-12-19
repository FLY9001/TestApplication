package com.eyesmart.testapplication.project;

import com.eyesmart.testapplication.R;
import com.eyesmart.testapplication.ui.TestView;
import com.eyesmart.testapplication.ui.viewwidget.MyAnim;

/**
 * 资源文件，可通过R类进行访问
 */

public class Resource {
    void test() {
        values();       //简单值xml

        anim();         //动画文件
        animator();     //属性动画文件
        drawable();     //图片文件、drawable的xml
        layout();       //布局文件
        menu();         //菜单定义文件
        mipmap();       //图标
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
        new MyAnim().test();
    }

    void animator() {
        int[] animator = {
                R.animator.property_animator,
        };
        new MyAnim().test();
    }

    void drawable() {
        int[] drawable = {
                R.drawable.xy,                  //普通位图
                R.drawable.message_left,        //9patch图片

                R.drawable.bitmap_drawable,     //相对普通图片，可设置更多效果
                R.drawable.layer_drawable,      //层级叠加图片，可设置SeekBar的外观
                R.drawable.levellist_drawable,  //根据等级显示图片
                R.drawable.shape_drawable,      //自定义图形
                R.drawable.select_drawable,     //根据状态显示图片

                R.drawable.frame_animation,     //帧动画
        };
        new MyAnim().test();
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
