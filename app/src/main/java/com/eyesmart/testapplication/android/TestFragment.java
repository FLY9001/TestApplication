package com.eyesmart.testapplication.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eyesmart.testapplication.R;

/**
 * Fragment全解析系列：http://www.jianshu.com/p/fd71d65f0ec6
 *
 * Fragment相对activity优点：1、内存占用低，2、响应速度快，3、灵活方便移植
 */

public class TestFragment extends Fragment {

    void test() {
        TestFragment tf = new TestFragment();       //可以在savedInstanceState==null时，才进行创建Fragment实例
        Bundle args = new Bundle();
        args.putString("key", "value");
        tf.setArguments(args);

        FragmentManager fm = getFragmentManager();  //Fragment的事务操作
        fm = getChildFragmentManager();             //获取本Fragment的Manager(用于Fragment嵌套)
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.add(R.id.content_main, tf);
        transaction.remove(tf);
        transaction.replace(R.id.content_main, tf);

        transaction.hide(tf);
        transaction.show(tf);
        transaction.attach(tf);
        transaction.detach(tf);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static TestFragment getInstance(String value) {  //只需要关心传递的数据
        TestFragment tf = new TestFragment();
        Bundle args = new Bundle();
        args.putString("key", value);
        tf.setArguments(args);                              //“内存重启”前，系统会帮你保存数据
        return tf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().get("key");

        setHasOptionsMenu(true);//可以在fragment中定义OptionsMenu
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        return rootView;
    }

    public void onClick(View v) {
        if (getActivity() instanceof FOneBtnClickListener) {        //交给宿主Activity处理，如果它希望处理
            ((FOneBtnClickListener) getActivity()).onFOneBtnClick();
        }
    }

    public interface FOneBtnClickListener {
        void onFOneBtnClick();
    }
}
