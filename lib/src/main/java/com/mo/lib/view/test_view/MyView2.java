package com.mo.lib.view.test_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


import com.mo.lib.utils.dataUtil.RandomUtil;
import com.mo.lib.utils.hardware_utils.ScreenUtil;
import com.mo.lib.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/10/16:17:02
 * @ 功能：
 */


public class MyView2 extends BaseView {


    public MyView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setColor(Color.RED);                      //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);   //设置画笔为空心     如果将这里改为Style.STROKE  这个图中的实线圆柱体就变成了空心的圆柱体
        color.add(Color.RED);
        color.add(Color.BLUE);
        color.add(Color.GRAY);
        color.add(Color.WHITE);
        color.add(Color.YELLOW);
    }
    private int temp = 0;
        private float radius = 100;
    private int height = 100;
    List<Integer> color = new ArrayList<>();
    private Canvas treeCanvas; // 缓存之前绘制的图片
    private Bitmap bitmap = null;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }
        if (treeCanvas == null) {
            treeCanvas = new Canvas(bitmap);
        }

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        treeCanvas.save();
        treeCanvas.translate(getWidth() / 2 - 217, getHeight() - 490);
        mPaint.setColor(color.get(RandomUtil.getInt(4)));
        treeCanvas.drawCircle((int) (ScreenUtil.getScreenWidth() * 0.5 ), (int) (ScreenUtil.getScreenHeight() * (temp + 0.1)), radius, mPaint);
        treeCanvas.save();
        treeCanvas.restore();
        temp += 0.1;
        radius -= 10;
        treeCanvas.restore();

        if (radius>1){
            invalidate();
        }
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

    private void action(Canvas canvas, int radius, float temp) {
        if (radius > 1) {
//            paint.setColor(color.get(RandomUtil.getInt(4)));
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle((int) (ScreenUtil.getScreenWidth() * 0.5 ), (int) (ScreenUtil.getScreenHeight() * (temp - 0.007)), radius, mPaint);
            canvas.save();
            canvas.restore();
            temp -= 0.007;

            action(canvas, radius -= 1, temp);
        }

    }




}