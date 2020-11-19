package com.mo.libdemo.action;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lib.view.recycle_view.KRecycleView;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:10
 * @ 功能：列表行为
 */
public interface ListAction<T> {
    List<T> getList();

    KRecycleView getRecycleView();

    BaseQuickAdapter getWrapRecyclerAdapter();


    default void setAdapter() {
        if (getRecycleView() != null && getWrapRecyclerAdapter() != null) {
            getRecycleView().setAdapter(getWrapRecyclerAdapter());
        }

    }
}
