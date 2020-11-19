package com.mo.lib.view.test_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.mo.lib.utils.dataUtil.RandomUtil;
import com.mo.lib.utils.hardware_utils.ScreenUtil;
import com.mo.lib.utils.tips_utils.LogUtil;
import com.mo.lib.view.BaseView;


/**
 * @ author：mo
 * @ data：2020/10/30:9:15
 * @ 功能：
 */
public class MyView3 extends BaseView {
    public MyView3(Context context) {
        super(context);
        mPaint.setColor(Color.RED);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        action(canvas, (float) (ScreenUtil.getScreenWidth() * 0.5),100, 1);
    }

    private void action(Canvas canvas, float ffff, int radius, float temp) {
        if (radius > 1) {
            canvas.drawCircle(ffff, (int) (ScreenUtil.getScreenHeight() * (temp - 0.007)), radius, mPaint);
//            canvas.save();
//            canvas.restore();
            canvas.translate(1,1);

            double anInt = (double) ((RandomUtil.getInt(2,10))*0.001);
            LogUtil.i(anInt+"");
            temp -= 0.007;

            action(canvas, (float) (ffff*1), radius -= 1, temp);
        }

    }

}
