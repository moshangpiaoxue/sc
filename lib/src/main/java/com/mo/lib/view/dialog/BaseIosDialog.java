package com.mo.lib.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mo.lib.R;
import com.mo.lib.utils.dataUtil.stringUtils.StringUtil;

/**
 * @ author：mo
 * @ data：2020/11/17:10:39
 * @ 功能：ios样式弹窗
 * 注：1、当控件样式如按钮颜色改变的时候，可继承此类，然后在构造方法中对对应的控件进行设置
 * 2、当所需视图与当前不同时，可继承此类，复写getLayoutId方法，返回自定义布局，自行进行设置，注意，自定义布局中必须包含此类中的5个控件id，不然会空指针
 */
public class BaseIosDialog extends BaseDialog {
    protected TextView titleView;
    protected TextView msgView;
    protected TextView tv_dialog_ios_left;
    protected TextView tv_dialog_ios_right;
    protected TextView tv_dialog_ios_line;

    public BaseIosDialog(Activity mActivity) {
        super(mActivity);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_ios;
    }

    @Override
    protected void doWhat(Dialog dialog, View view) {
        titleView = (TextView) view.findViewById(R.id.tv_dialog_ios_title);
        msgView = (TextView) view.findViewById(R.id.tv_dialog_ios_msg);
        tv_dialog_ios_left = (TextView) view.findViewById(R.id.tv_dialog_ios_left);
        tv_dialog_ios_right = (TextView) view.findViewById(R.id.tv_dialog_ios_right);
        tv_dialog_ios_line = (TextView) view.findViewById(R.id.tv_dialog_ios_line);
    }

    /** 设置title */
    public BaseIosDialog setTitle(String title) {
        if (StringUtil.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
            return this;
        }
        titleView.setText(title);
        return this;
    }

    /** 设置内容 */
    public BaseIosDialog setMsg(String msg) {
        if (StringUtil.isEmpty(msg)) {
            msgView.setVisibility(View.GONE);
            return this;
        }
        msgView.setText(msg);
        return this;
    }

    /** 设置内容排列方式 */
    public BaseIosDialog setMsgGravity(int gravity) {
        msgView.setGravity(gravity);
        return this;
    }


    public BaseIosDialog setLeftTextView(String leftTxt, OnClickListener listener) {
        if (StringUtil.isEmpty(leftTxt)) {
            tv_dialog_ios_left.setVisibility(View.GONE);
            tv_dialog_ios_line.setVisibility(View.GONE);
            return this;
        }
        tv_dialog_ios_left.setText(leftTxt);

        tv_dialog_ios_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                dismiss();
            }
        });


        return this;
    }

    public BaseIosDialog setRightTextView(String rightTxt, OnClickListener listener) {
        if (StringUtil.isEmpty(rightTxt)) {
            tv_dialog_ios_right.setVisibility(View.GONE);
            tv_dialog_ios_line.setVisibility(View.GONE);
            return this;
        }
        tv_dialog_ios_right.setText(rightTxt);
        tv_dialog_ios_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(view);
                }
                dismiss();
            }
        });
        return this;
    }


}
