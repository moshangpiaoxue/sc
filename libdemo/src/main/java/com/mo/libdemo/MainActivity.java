package com.mo.libdemo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lib.action.LoadTypeAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.dataUtil.date.DateUtil;
import com.mo.lib.utils.tips_utils.LogUtil;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.libdemo.activitys.animation_effect.AnimationEffectsActivity;
import com.mo.libdemo.activitys.function.FunctionsActivity;
import com.mo.libdemo.activitys.widget.WidgetsActivity;
import com.mo.libdemo.bean.MainBean;
import com.mo.libdemo.modle.AdapterModle;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/libdemo/main")
public class MainActivity extends BaseActivity implements ListAction<MainBean>, LoadTypeAction {

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
        setTitle("主页");
        getRecycleView().setLayoutGrid(4);
        LogUtil.i("ssssssssssssssssss");
        setAdapter();
//        getLoadTypeView().setLoadingView(ViewUtil.getView(getActivity(), com.mo.libsx.R.layout.base_error));
//        getLoadTypeView().showLoading();
//        getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadSuccess();
//            }
//        },2000);
        LogUtil.i(DateUtil.getString(-1420099200));
//        LogUtil.i(DateUtil.getString(Math.abs(-1420099200)));
//        LogUtil.i(DateUtil.getString(
//                (new SimpleDate(-1420099200).getDate()
//                )));
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("控件widget", WidgetsActivity.class));
        list.add(new MainBean("动画效果", AnimationEffectsActivity.class));
        list.add(new MainBean("功能", FunctionsActivity.class));
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
