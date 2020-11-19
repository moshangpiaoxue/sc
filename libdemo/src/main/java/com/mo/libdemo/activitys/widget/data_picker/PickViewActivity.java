package com.mo.libdemo.activitys.widget.data_picker;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.dataUtil.date.DateUtil;
import com.mo.sc.libdemo.R;

import java.util.Calendar;
import java.util.Date;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：PickerView
 */
public class PickViewActivity extends BaseActivity implements TitleBarAction {

    private TextView tv_pickview_date;
    private TimePickerView timePickerView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_datepicker_pickview;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_pickview_date) {
            timePickerView.show();
        }
    }

    @Override
    protected void initView() {
        setTitle("PickerView");
        tv_pickview_date = findViewById(R.id.tv_pickview_date);

        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 0, 1);
        Calendar endDate = Calendar.getInstance();

        timePickerView = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //给月份赋值
                tv_pickview_date.setText(DateUtil.getString(date, "yyyy-MM"));
            }
        }).isCyclic(false)
                .setCancelText("取消")
                .setSubmitText("确定")
                //                        .setTitleText("选择月份")
                .setRangDate(startDate, endDate)
                .setDate(Calendar.getInstance())
                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .build();
    }

    @Override
    protected void initData() {

    }

}
