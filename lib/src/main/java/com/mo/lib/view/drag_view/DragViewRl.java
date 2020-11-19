package com.mo.lib.view.drag_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import com.mo.lib.utils.tips_utils.LogUtil;

/**
 * @ author：mo
 * @ data：2020/10/9:14:13
 * @ 功能：
 */
public class DragViewRl extends RelativeLayout {

    private ViewDragHelper mDragger;

    private View mAutoBackView, mAutoBackView1;

    public DragViewRl(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public int getViewHorizontalDragRange(View child) {
                //返回可拖动的子视图的水平运动范围(以像素为单位)的大小。
                //对于不能垂直移动的视图，此方法应该返回0。
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                //返回可拖动的子视图的竖直运动范围(以像素为单位)的大小。
                //对于不能垂直移动的视图，此方法应该返回0。
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //返回true表view示捕获当前touch到的
                return child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > getWidth() - child.getMeasuredWidth()) {
                    //超出左侧边界处理
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {
                    //超出右侧边界处理
                    left = 0;
                }
                LogUtil.i("left=="+left +"\t"+"getWidth()=="+getWidth() +"\t"+"child.getMeasuredWidth()=="+child.getMeasuredWidth());
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - child.getMeasuredHeight()) {
                    //超出下边界处理
                    top = getHeight() - child.getMeasuredHeight();
                } else if (top < 0) {
                    //超出上边界处理
                    top = 0;
                }
                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                float x = releasedChild.getX();
                float y = releasedChild.getY();
                if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) {
                    x = 0;
                } else {
                    x = getMeasuredWidth() - releasedChild.getMeasuredWidth() ;
                }
                Log.e("dbj", "x=" + x + ",y=" + y);
                // 移动到指定位置
                mDragger.settleCapturedViewAt((int) x, (int) y);
                invalidate();
                super.onViewReleased(releasedChild, xvel, yvel);
                /**
                 * 可以贴上下左右四边的代码
                 float x = releasedChild.getX();
                 float y = releasedChild.getY();
                 if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) { // 0-x/2
                 if (x < releasedChild.getMeasuredWidth() / 3f) {
                 x = 0;
                 } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                 y = 0;
                 } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                 y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                 } else {
                 x = 0;
                 }
                 } else { // x/2-x
                 if (x > getMeasuredWidth() - releasedChild.getMeasuredWidth() / 3f - releasedChild.getMeasuredWidth()) {
                 x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                 } else if (y < (releasedChild.getMeasuredHeight() * 3)) { // 0-y/3
                 y = 0;
                 } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) { // 0-(y-y/3)
                 y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                 } else {
                 x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                 }
                 }
                 // 移动到x,y
                 mDragger.settleCapturedViewAt((int) x, (int) y);
                 invalidate();
                 super.onViewReleased(releasedChild, xvel, yvel);
                 **/
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //位置发生改变时回调
//                viewFollowChanged(dx, dy);
            }
        });
    }

    private void viewFollowChanged(int dx, int dy) {
        mAutoBackView1.offsetTopAndBottom(dy);
        mAutoBackView1.offsetLeftAndRight(dx);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * onFinishInflate 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAutoBackView = getChildAt(0);
        mAutoBackView1 = getChildAt(1);
    }
}

