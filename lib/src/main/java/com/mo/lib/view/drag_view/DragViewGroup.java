package com.mo.lib.view.drag_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import com.mo.lib.utils.tips_utils.LogUtil;


/**
 * @ author：mo
 * @ data：2020/10/9:13:26
 * @ 功能：
 */
public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mDragHelper;
    /** 是否拖拽后的回弹，也就是松开手指后，回到初始位置 */
    private boolean isReturnHome = false;
    /** 边缘触发 */
    private boolean isEdgeTrigger = true;
    private int mDragOriLeft;
    private int mDragOriTop;

    public DragViewGroup(Context context) {
        this(context, null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            /** 返回 true 时才会导致下面的回调方法被调用  */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mDragOriLeft = capturedChild.getLeft();
                mDragOriTop = capturedChild.getTop();
            }

            /** 处理 child 拖拽时的位置坐标*/
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                LogUtil.i("left==" + left + "\t" + "getWidth()==" + getWidth() + "\t" + "child.getMeasuredWidth()==" + child.getMeasuredWidth());
                return left;
            }

            /** 处理 child 拖拽时的位置坐标*/
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                LogUtil.i("top==" + top + "\t" + "getHeight()==" + getHeight() + "\t" + "child.getMeasuredHeight()==" + child.getMeasuredHeight());
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (isReturnHome) {
                    mDragHelper.settleCapturedViewAt(mDragOriLeft, mDragOriTop);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);

                if (isEdgeTrigger) {
                    LogUtil.i("onEdgeDragStarted: " + edgeFlags);
                    mDragHelper.captureChildView(getChildAt(getChildCount() - 1), pointerId);
                }

            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                if (isEdgeTrigger) {
                    LogUtil.i("onEdgeTouched: " + edgeFlags);

                }
            }
        });
        if (isEdgeTrigger) {
            mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (isReturnHome && mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

}
