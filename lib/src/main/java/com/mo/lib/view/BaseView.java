package com.mo.lib.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ author：mo
 * @ data：2020/11/16:14:07
 * @ 功能：基础view
 */
public class BaseView extends View {
    protected Paint mPaint = new Paint();
    protected int mWidth = 0;
    //当前控件高度
    protected int mHeight = 0;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
