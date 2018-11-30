package com.eyesmart.testapplication.android;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eyesmart.testapplication.R;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * implementation 'com.jakewharton:butterknife:8.6.0'
 * annotationProcessor 'com.jakewharton:butterknife-compiler:8.6.0'
 *
 * 插件android butterknife zelezny，可以自动生成绑定代码
 */
public class TestButterKnife extends AppCompatActivity {

    /**
     * 绑定控件
     */
    @BindView(R.id.textView)
    TextView textView;

    //绑定控件集合
    //@BindViews({ R.id.first_name, R.id.middle_name, R.id.last_name })
    //List<EditText> nameViews;

    /**
     * 绑定资源
     */
    @BindString(R.string.app_name)
    String app_name;
    @BindDrawable(R.drawable.bitmap_drawable)
    Drawable bitmap_drawable;
    @BindColor(R.color.colorAccent)
    int colorAccent;
    @BindDimen(R.dimen.fab_margin)
    Float fab_margin;

    /**
     * 绑定控件事件
     */
    @OnClick(R.id.textView)//注解控件
    public void test(View view) {//点击控件后触发的方法，参数可选
    }

    //绑定多个控件事件
    @OnClick({R.id.textView})
    public void test2(View view) {
        switch (view.getId()) {
            case R.id.textView:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);

        //在当前activity自动绑定
        ButterKnife.bind(this);
        //ButterKnife.bind(this, rootView); //Fragment

        textView.setText("@BindView(R.id.textview) 绑定View");
    }
}
