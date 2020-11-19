package com.mo.lib.view.drag_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;


/**
 * @ author：mo
 * @ data：2020/10/9:13:26
 * @ 功能：自定义可拖动ImageView
 */
public class DragImageView extends AppCompatImageView {

    private float xDistance;
    private float yDistance;
    private int l;
    private int r;
    private int t;
    private int b;
    private float upX;
    private float distanceX;
    private Scroller scroller;
    public static boolean iscanclick = false;
    private int xdown;

    public DragImageView(Context context) {
        super(context);
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(getContext());
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }


    private float downX;
    private float downY;
    private long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iscanclick = false;
                return true;
            }
        });
        //        this.setOnClickListener(new OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                iscanclick = true;
        //            }
        //        });
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();

                    int[] locationdown = new int[2];
                    this.getLocationOnScreen(locationdown);
                    xdown = locationdown[0];
                    break;
                case MotionEvent.ACTION_MOVE:

                    xDistance = event.getX() - downX;
                    yDistance = event.getY() - downY;
                    if (xDistance != 0 && yDistance != 0) {
                        l = (int) (getLeft() + xDistance);
                        r = (int) (getRight() + xDistance);
                        t = (int) (getTop() + yDistance);
                        b = (int) (getBottom() + yDistance);
                        this.layout(l, t, r, b);

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    int[] location1 = new int[2];
                    this.getLocationOnScreen(location1);
                    int x12 = location1[0];
                    int i1 = x12 - xdown;
                    int abs = Math.abs(i1);
                    if (abs > 100) {
                        iscanclick = false;
                    } else {
                        iscanclick = true;

                    }


                    float overx = event.getX();
                    scroller = new Scroller(getContext());
                    setPressed(false);
                    int screenHeight = getScreenHeight(getContext());
                    int screenWidth = getScreenWidth(getContext());
                    Log.e("ASDF", "onTouchEvent: " + screenHeight);
                    Log.e("ASDF", "onTouchEvent: " + screenWidth);
                    int i = screenWidth / 2;
                    if (x12 < i) {
                        //左边
                        //                        smoothScrollBack(x12);
                        this.layout(0, t, this.getWidth(), b);
                    } else {
                        //右边
                        //                        smoothScrollBack(x12);
                        this.layout(screenWidth - this.getWidth(), t, screenWidth, b);
                    }

                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private void smoothScrollBack(int x12) {
        // 初始化Scroller
        int startX = x12;// 起点位置
        int dx = 0 - startX;// 终点减起点
        int duration = Math.abs(dx) * 10;// 从终点移动到起点耗时
        scroller.startScroll(startX, 0, 0, 0, duration);
        computeScroll();
    }

    // 平滑移动时，结合Scroller一块使用
    @Override
    public void computeScroll() {
        //        scroller = new Scroller(getContext());
        // 调用Scroller计算方法
        if (scroller.computeScrollOffset()) {// 计算出来一个中间位置 , 返回指如果为false，说明已经移动到终点了
            int currX = scroller.getCurrX();// 得到计算的一个中间位置
            // 移动到一个中间位置
            scrollToX(currX);
            // 重新触发computeScroll
            invalidate();
        }
    }

    // 封装scrollTo方法，正数往右移动，负数往左移动
    private void scrollToX(int x) {
        scrollTo(-x, 0);
    }
}
