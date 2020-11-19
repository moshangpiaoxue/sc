package com.mo.libdemo.activitys.widget.data_picker;

import android.widget.DatePicker;

import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.sc.libdemo.R;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：系统控件DatePicker
 */
public class DefDataPickerActivity extends BaseActivity implements TitleBarAction {
    private DatePicker dp_view_datepicker_def;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_datepicker_def;
    }

    @Override
    protected void initView() {
        setTitle("系统控件DatePicker");
        dp_view_datepicker_def = findViewById(R.id.dp_view_datepicker_def);
    }

    @Override
    protected void initData() {

    }

}
