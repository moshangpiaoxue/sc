package com.mo.lib.view.indext_view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mo.lib.utils.dataUtil.dealUtil.DensityUtil;
import com.mo.lib.view.BaseView;

/**
 * @ author：mo
 * @ data：2020/11/17:13:05
 * @ 功能：
 */
public class BaseIndexView extends BaseView {
    /** 索引备选数据1 */
    public static final String[] mData1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /** 索引备选数据2 */
    public static final String[] mData2 = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /** 真正的索引数据，默认不带定位热门 */
    public String[] mData = mData1;

    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /** 关联的提示tv */
    protected TextView mTipsView;
    /** 正常显示的颜色 */
    protected int mNormalTextColor;
    /** 按中时显示的颜色 */
    protected int mPressedTextColor;

    protected OnTouchLetterChangedListener mListener;

    public interface OnTouchLetterChangedListener {
        void onTouchLetterChanged(String s, int index);

        void onTouchActionUp(String s);
    }

    public BaseIndexView(Context context) {
        this(context, null);
    }

    public BaseIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNormalTextColor = Color.parseColor("#3A8EFF");
        mPressedTextColor = Color.parseColor("#F50527");
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(DensityUtil.sp2px(14));
    }
    /**
     * 设置关联TV
     */
    public void setTipsView(TextView tipTv) {
        mTipsView = tipTv;
    }
    /**
     * 设置索引数据可自定义但必须是排好序的
     */
    public void setData(String[] mData) {
        this.mData = mData;
        invalidate();
    }
    public void setOnTouchLetterChangedListener(OnTouchLetterChangedListener listener) {
        this.mListener = listener;
    }

}
