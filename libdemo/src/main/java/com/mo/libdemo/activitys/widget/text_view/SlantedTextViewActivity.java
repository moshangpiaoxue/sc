package com.mo.libdemo.activitys.widget.text_view;


import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.sc.libdemo.R;

/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：旋转TextView
 */
public class SlantedTextViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textview_slantedtv;
    }

    @Override
    protected void initView() {
        setTitle("旋转TextView");
    }

    @Override
    protected void initData() {

    }

}
