package com.mo.lib.view.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;


/**
 * @ author：mo
 * @ data：2017/12/10：11:58
 * @ 功能：自定义viewpage
 */
public class KViewPage extends ViewPager {
    /**
     * 是否可以左右滑动
     */
    private boolean isCanScroll = true;
    private Scroller mScroller; // 滑动控件
    public KViewPage(Context context) {
        this(context,null);
    }

    public KViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件不触发
        return isCanScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 不处理触摸拦截事件
        return isCanScroll && super.onInterceptTouchEvent(event);
    }


    /**
     * 设置是否可以滑动
     *
     * @param isCanScroll false 不能滑动， true 可以滑动
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    /**
     * 设置预加载页数
     *
     * @param num
     */
    public void setPreloadingNum(int num) {
        this.setOffscreenPageLimit(num);
    }
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }

    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 处理vp高度为自适应时，显示会出问题的bug
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        // 分发事件，这个是必须要的，如果把这个方法覆盖了，那么ViewPager的子View就接收不到事件了
//        if (this.enabled) {
//            return super.dispatchTouchEvent(event);
//        }
//        return super.dispatchTouchEvent(event);
//    }

}
