package com.mo.libdemo.activitys.function;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.libdemo.bean.MainBean;
import com.mo.libdemo.modle.AdapterModle;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：功能
 */
public class FunctionsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

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
        setTitle("功能");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("拍照", TakePhotoActivity.class));
        list.add(new MainBean("签字", SignatureViewActivity.class));
        list.add(new MainBean("二维码", ScanActivity.class));
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
