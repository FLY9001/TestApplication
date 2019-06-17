package com.eyesmart.testapplication.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eyesmart.testapplication.R;


/**
 * 8种基本对话框：https://www.cnblogs.com/gzdaijie/p/5222191.html
 *
 * 自定义密码对话框
 */
public class PasswordDialog {
    private Context mCon;
    private EditText et_password;
    private TextView tv_password_error;
    private Button btn_cancel;
    private Button btn_sure;
    private ClickListener listener;
    private Dialog dialog;

    public PasswordDialog(Context c) {
        mCon = c;
        initView();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;

    }

    private void initView() {
        dialog = new Dialog(((Activity) mCon), R.style.password_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_password);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);

        et_password = (EditText) dialog.findViewById(R.id.et_password);
        tv_password_error = (TextView) dialog.findViewById(R.id.tv_password_error);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_sure = (Button) dialog.findViewById(R.id.btn_sure);

        et_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //et_password.setBackgroundResource(R.drawable.blank_edit);
                tv_password_error.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable text = et_password.getText();
                listener.onCickEnsure(text.toString());

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClickCancel();
            }
        });
    }

    public void showErrorMsg(boolean flag) {

        if (flag) {

            tv_password_error.setVisibility(View.VISIBLE);
            //et_password.setBackgroundResource(R.drawable.red_rect_edit);
        } else {
            tv_password_error.setVisibility(View.INVISIBLE);
            //et_password.setBackgroundResource(R.drawable.blank_edit);
        }

    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }


    public interface ClickListener {

        void onCickEnsure(String msg);

        void onClickCancel();
    }
}

