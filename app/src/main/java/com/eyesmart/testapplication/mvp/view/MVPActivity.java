package com.eyesmart.testapplication.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eyesmart.testapplication.R;
import com.eyesmart.testapplication.mvp.model.FindItemsModelImlp;
import com.eyesmart.testapplication.mvp.presenter.MVPPresenter;
import com.eyesmart.testapplication.mvp.presenter.MVPPresenterImpl;

import java.util.List;

public class MVPActivity extends AppCompatActivity implements MVPView {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private MVPPresenter mMVPPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mRecyclerView = findViewById(R.id.list);
        mProgressBar = findViewById(R.id.progress);

        mMVPPresenter = new MVPPresenterImpl(this, new FindItemsModelImlp());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMVPPresenter.onResume();
    }


    @Override
    protected void onDestroy() {
        mMVPPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<String> items) {
        mRecyclerView.setAdapter(new MVPAdapter(items, mMVPPresenter));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}