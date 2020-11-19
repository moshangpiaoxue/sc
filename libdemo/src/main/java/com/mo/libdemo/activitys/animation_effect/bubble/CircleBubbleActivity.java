package com.mo.libdemo.activitys.animation_effect.bubble;


import android.view.View;

import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.tips_utils.LogUtil;
import com.mo.lib.view.animation_effect.BubbleLayout;
import com.mo.sc.libdemo.R;

/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：气泡从水底升起动画
 */
public class CircleBubbleActivity extends BaseActivity implements TitleBarAction {

    private BubbleLayout bl_;

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_bublle_circle;
    }


    @Override
    protected void initView() {
        setTitle("气泡从水底升起动画");
        bl_ = findViewById(R.id.bl_);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bl_.setIsVisible(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bl_.setIsVisible(false);

    }

    @Override
    protected void initData() {

    }

}
