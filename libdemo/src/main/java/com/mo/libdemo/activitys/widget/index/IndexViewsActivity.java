package com.mo.libdemo.activitys.widget.index;

import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.indext_view.IndexBezierView;
import com.mo.sc.libdemo.R;

/**
 * @ author：mo
 * @ data：2020/11/17:11:29
 * @ 功能：索引view相关
 */
public class IndexViewsActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.act_view_index;
    }

    @Override
    protected void initView() {
        IndexBezierView ll_sticky_letter = findViewById(R.id.ll_sticky_letter);
    }

    @Override
    protected void initData() {

    }
}
