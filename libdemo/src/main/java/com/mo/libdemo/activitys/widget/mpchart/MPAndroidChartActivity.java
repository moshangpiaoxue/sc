package com.mo.libdemo.activitys.widget.mpchart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.dataUtil.RandomUtil;
import com.mo.lib.utils.tips_utils.LogUtil;
import com.mo.lib.view.mp_android_chart.DynamicLineChartManager;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/11/18:9:53
 * @ 功能：
 */
public class MPAndroidChartActivity extends BaseActivity {
    private LineChart lc_mpchart;
    private DynamicLineChartManager dynamicLineChartManager2;
    private int maxTop = 50;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_mpchart;
    }

    @Override
    protected void initView() {
        lc_mpchart = findViewById(R.id.lc_mpchart);
        refrashLine();
    }

    private void refrashLine() {
        //波浪线
        List<String> names = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        names.add("");
        names.add("");
        colors.add(Color.rgb(64, 160, 254));
        colors.add(Color.rgb(212, 66, 218));
        dynamicLineChartManager2 = new DynamicLineChartManager(lc_mpchart, names, colors);
        if (maxTop == 50) {
            dynamicLineChartManager2.setYAxis(maxTop, 0, getLabelCount(50));
        }
        dynamicLineChartManager2.setDescription("折线图");
        runChart();
    }

    private void runChart() {
        List<Integer> mLinedate = new ArrayList<>();
        int line1 = RandomUtil.getInt(200, 300);
        int line2 = RandomUtil.getInt(100, 200);
        mLinedate.add(line1);
        mLinedate.add(line2);
        initLines(line1, line2);
        dynamicLineChartManager2.addEntry(mLinedate);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runChart();
            }
        }, 3000);
    }

    private int getLabelCount(int maxTop) {
        LogUtil.i("maxTop==" + maxTop + "this.maxTop==" + this.maxTop);

        if (maxTop == 50) {
            this.maxTop = 50;
            return maxTop / 10 + 1;
        } else if (maxTop > 50 && maxTop < 100) {
            this.maxTop = 100;
            return this.maxTop / 20;
        } else {
            this.maxTop = (int) (Math.ceil(maxTop / 100) + 1) * 100;
            return this.maxTop / 100;
        }
    }

    private void initLines(Integer dianliu, Integer gonglv) {
        int max = Math.max(dianliu, gonglv);
        int scy = max > 50 && max < 100 ? 20 : 50;
        int tem = (int) (Math.ceil(max / scy) + 1) * scy;
        if (tem > maxTop) {
            int label = getLabelCount(tem);
            dynamicLineChartManager2.setYAxis(maxTop, 0, label);
        }
    }

    @Override
    protected void initData() {

    }
}
