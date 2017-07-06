package com.eyesmart.testapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Tian on 2017-7-6 0006.
 */

public class TestFragment extends Fragment {

    void test() {
        TestFragment tf = new TestFragment();       //可以在savedInstanceState==null时，才进行创建Fragment实例
        Bundle args = new Bundle();
        args.putString("key", "value");
        tf.setArguments(args);

        FragmentManager fm = getFragmentManager();
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

    public static TestFragment getInstance(String value) {
        TestFragment tf = new TestFragment();
        Bundle args = new Bundle();
        args.putString("key", value);
        tf.setArguments(args);
        return tf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments().get("key");

        setHasOptionsMenu(true);     //可以在fragment中定义OptionsMenu
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
