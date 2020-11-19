package com.mo.libdemo.activitys.animation_effect;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.libdemo.activitys.animation_effect.bubble.BubblesActivity;
import com.mo.libdemo.activitys.animation_effect.floating.FloatingActivity;
import com.mo.libdemo.activitys.animation_effect.snow.SnowActivity;
import com.mo.libdemo.bean.MainBean;
import com.mo.libdemo.modle.AdapterModle;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：动画效果
 */
public class AnimationEffectsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ld_activity_main;
    }

    @Override
    protected void initView() {
        setTitle("动画效果");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("气泡动效", BubblesActivity.class));
        list.add(new MainBean("红旗飘", FloatingActivity.class));
        list.add(new MainBean("落雪", SnowActivity.class));
        return list;
    }

    @Override
    public KRecycleView getRecycleView() {
        return findViewById(R.id.krv_main);
    }

    @Override
    public BaseQuickAdapter getWrapRecyclerAdapter() {
        return AdapterModle.getMainAdapter(getActivity(), getList());
    }
}
