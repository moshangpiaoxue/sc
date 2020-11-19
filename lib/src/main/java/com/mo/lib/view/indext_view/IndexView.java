package com.mo.lib.view.indext_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mo.lib.utils.dataUtil.dealUtil.DensityUtil;

/**
 * @ author：mo
 * @ data：2018/8/13:16:54
 * @ 功能：字母索引view
 */
public class IndexView extends BaseIndexView {

    /** 默认不选中 */
    private int mSelected = -1;
    /** 画笔 */
//    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /** 选中监听 */
    private IndexViewSelectedListener mSelectedListener;




    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int singleHeight = getHeight() / mData.length;

        for (int i = 0; i < mData.length; i++) {
            mPaint.setColor(mNormalTextColor);
            mPaint.setTypeface(Typeface.DEFAULT);
            // 选中的状态
            if (i == mSelected) {
                mPaint.setColor(mPressedTextColor);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - mPaint.measureText(mData[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mData[i], xPos, yPos, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newSelected = (int) (event.getY() / getHeight() * mData.length);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mSelected = -1;
                invalidate();
                if (mTipsView != null) {
                    mTipsView.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (mSelected != newSelected) {
                    if (newSelected >= 0 && newSelected < mData.length) {
                        if (mSelectedListener != null) {
                            mSelectedListener.onIndexViewSelected(this, mData[newSelected]);
                        }
                        if (mTipsView != null) {
                            mTipsView.setText(mData[newSelected]);
                            mTipsView.setVisibility(View.VISIBLE);
                        }
                        mSelected = newSelected;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 设置选中监听
     */
    public void setSelectedListener(IndexViewSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }




    public interface IndexViewSelectedListener {
        void onIndexViewSelected(IndexView indexView, String text);
    }

}