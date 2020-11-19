package com.mo.libdemo.activitys.widget;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.libdemo.activitys.widget.data_picker.DataPickerActivity;
import com.mo.libdemo.activitys.widget.dialog.DialogsActivity;
import com.mo.libdemo.activitys.widget.edit_view.EditsViewActivity;
import com.mo.libdemo.activitys.widget.image_view.ImagesViewActivity;
import com.mo.libdemo.activitys.widget.index.IndexViewsActivity;
import com.mo.libdemo.activitys.widget.keyboard.KeyBoardActivity;
import com.mo.libdemo.activitys.widget.list.ListsActivity;
import com.mo.libdemo.activitys.widget.mpchart.MPAndroidChartActivity;
import com.mo.libdemo.activitys.widget.popwindow.PopWindowsActivity;
import com.mo.libdemo.activitys.widget.progress_bar.ProgressBarsActivity;
import com.mo.libdemo.activitys.widget.text_view.TextViewsActivity;
import com.mo.libdemo.activitys.widget.view_switcher.ViewSwitcherActivity;
import com.mo.libdemo.activitys.widget.viewpage.ViewPagesActivity;
import com.mo.libdemo.bean.MainBean;
import com.mo.libdemo.modle.AdapterModle;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：控件界面
 */
@Route(path = "/libdemo/widget")
public class WidgetsActivity extends BaseActivity implements TitleBarAction, ListAction<MainBean> {

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
        setTitle("控件widget");
        getRecycleView().setLayoutGrid(4);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override

    public List<MainBean> getList() {
        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean("TextView", TextViewsActivity.class));
        list.add(new MainBean("EditView", EditsViewActivity.class));
        list.add(new MainBean("ImageView", ImagesViewActivity.class));
        list.add(new MainBean("IndexViews", IndexViewsActivity.class));
        list.add(new MainBean("KeyBoard", KeyBoardActivity.class));
        list.add(new MainBean("MPAndroidChart", MPAndroidChartActivity.class));
        list.add(new MainBean("ProgressBar", ProgressBarsActivity.class));
        list.add(new MainBean("列表", ListsActivity.class));
        list.add(new MainBean("ViewSwitcher", ViewSwitcherActivity.class));
        list.add(new MainBean("ViewPage", ViewPagesActivity.class));
        list.add(new MainBean("PopWindow", PopWindowsActivity.class));
        list.add(new MainBean("日期选择", DataPickerActivity.class));
        list.add(new MainBean("Dialog", DialogsActivity.class));
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
