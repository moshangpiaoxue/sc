//package com.mo.lib.view.test_view;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.view.View;
//
//import com.mo.lib.utils.hardware_utils.ScreenUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @ author：mo
// * @ data：2020/10/16:17:02
// * @ 功能：
// */
//public class MyView1 extends BaseView {
//
//
//    //    public MyView1(Context context) {
//    //        this(context, null);
//    //    }
//    //
//    //    public MyView1(Context context, AttributeSet attrs) {
//    //        this(context, attrs, 0);
//    //    }
//    //
//    //    public MyView1(Context context, AttributeSet attrs, int defStyleAttr) {
//    //        super(context, attrs, defStyleAttr);
//
//    //    }
//
//    private int temp = 0;
//    //    private float radius = 100;
//    private int height = 100;
//    List<Integer> color = new ArrayList<>();
//
////    public MyView1(Context context, AttributeSet attrs) {
////        super(context, attrs);
////    }
//
//    public MyView1(Context context) {
//        super(context,null);
//        mPaint.setColor(Color.RED);                      //设置画笔颜色
//        mPaint.setStyle(Paint.Style.FILL);   //设置画笔为空心     如果将这里改为Style.STROKE  这个图中的实线圆柱体就变成了空心的圆柱体
//
//        color.add(Color.RED);
//        color.add(Color.BLUE);
//        color.add(Color.GRAY);
//        color.add(Color.WHITE);
//        color.add(Color.YELLOW);
//    }
//
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        action(canvas, 100, 1);
//    }
//
//    private void action(Canvas canvas, int radius, float temp) {
//        if (radius > 1) {
//            //            paint.setColor(color.get(RandomUtil.getInt(4)));
//            mPaint.setColor(Color.GREEN);
//            canvas.drawCircle((int) (ScreenUtil.getScreenWidth() * 0.5), (int) (ScreenUtil.getScreenHeight() * (temp - 0.007)), radius, mPaint);
//            canvas.save();
//            canvas.restore();
//            temp -= 0.007;
//
//            action(canvas, radius -= 1, temp);
//        }
//
//    }
//
//
//    @Override
//    public void onClick(View view) {
//
//    }
//}