package com.mo.lib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.mo.lib.R;


/**
 * @ author：mo
 * @ data：2019/5/13:9:05
 * @ 功能：
 */
public class LoadingDialog extends BaseDialog {
    //提示信息
    private String tips;
    //透明度
    private TextView tv_dialog_loading;


    public LoadingDialog(Activity mActivity, String tips) {
        super(mActivity);
        this.tips = tips;
        if (tips.isEmpty()) {
            tv_dialog_loading.setVisibility(View.GONE);
        } else {
            tv_dialog_loading.setVisibility(View.VISIBLE);
            tv_dialog_loading.setText(tips);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void doWhat(Dialog dialog, View view) {
        tv_dialog_loading = view.findViewById(R.id.tv_dialog_loading);
    }




}
