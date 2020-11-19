package com.mo.libdemo.activitys.widget.image_view;


import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.sc.libdemo.R;

/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：ImageView相关
 */
public class ImagesViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_imagesview;
    }

    @Override
    protected void initView() {
        setTitle("ImageView相关");
    }

    @Override
    protected void initData() {

    }

}
