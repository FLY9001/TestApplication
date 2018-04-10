package com.eyesmart.testapplication.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.eyesmart.testapplication.R;
import com.eyesmart.testapplication.mvp.presenter.UserPresenter;

public class MVPActivity extends AppCompatActivity implements IUserView {
    private EditText edt_id, edt_first, edt_last;
    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_first = (EditText) findViewById(R.id.edt_id);
        edt_last = (EditText) findViewById(R.id.edt_id);

        mUserPresenter = new UserPresenter(this);
        mUserPresenter.loadUser(0);
    }

    public void onSave(View view) {
        mUserPresenter.saveUser(getID(), getFristName(), getLastName());
    }

    @Override
    public int getID() {
        return Integer.parseInt(edt_id.getText().toString());
    }

    @Override
    public String getFristName() {
        return edt_first.getText().toString();
    }

    @Override
    public String getLastName() {
        return edt_last.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        edt_first.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        edt_last.setText(lastName);
    }

}
