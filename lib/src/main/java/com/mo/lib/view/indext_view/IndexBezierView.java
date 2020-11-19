package com.mo.lib.view.indext_view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.mo.lib.R;
import com.mo.lib.utils.dataUtil.dealUtil.DensityUtil;
import com.mo.lib.utils.tips_utils.LogUtil;

/**
 * @ author：mo
 * @ data：2020/11/17:11:43
 * @ 功能：字母索引view 贝塞尔曲线 实
 */
public class IndexBezierView extends BaseIndexView {

    // 向右偏移多少画字符， default 30
    private float mWidthOffset = 30f;

    // 最小字体大小
    private int mMinFontSize = 55;

    // 最大字体大小
    private int mMaxFontSize = 88;

    // 提示字体大小
    private int mTipFontSize = 52;

    // 提示字符的额外偏移
    private float mAdditionalTipOffset = 20.0f;

    // 贝塞尔曲线控制的高度
    private float mMaxBezierHeight = 350.0f;

    // 贝塞尔曲线单侧宽度
    private float mMaxBezierWidth = 340.0f;

    // 贝塞尔曲线单侧模拟线量
    private int mMaxBezierLines = 32;
    /** 抬起后选中字符是否恢复原来颜色 */
    private boolean isRecoverColor = true;

    private int mConLength = 0;

    private int mChooseIndex = -1;
    private PointF mTouch = new PointF();

    private PointF[] mBezier1;
    private PointF[] mBezier2;

    private float mLastOffset[]; // 记录每一个字母的x方向偏移量, 数字<=0

    private Scroller mScroller;
    //正在动画
    private boolean mAnimating = false;
    //动画的偏移量
    private float mAnimationOffset;

    //动画隐藏
    private boolean mHideAnimation = false;

    //手指是否抬起
    private boolean isUp = false;

    private int mAlpha = 255;

    /**
     * 控制距离顶部的距离、底部距离
     */
    private int paddingTop = 0;
    private int paddingBottom = 0;

    @SuppressLint("HandlerLeak")
    Handler mHideWaitingHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //				mScroller.startScroll(0, 0, 255, 0, 1000);
                mHideAnimation = true;
                mAnimating = false;                                                        //动画mAnimating=false onDraw触发
                if (isRecoverColor) {
                    //颜色还原
                    IndexBezierView.this.invalidate();
                }
                return;
            }
            super.handleMessage(msg);
        }
    };

    public IndexBezierView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context, attrs);
    }

    public IndexBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
    }

    public IndexBezierView(Context context) {
        super(context);
        initData(null, null);
    }

    private void initData(Context context, AttributeSet attrs) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndexBezierView);
            mWidthOffset = a.getDimension(R.styleable.IndexBezierView_widthOffset, mWidthOffset);
            mMinFontSize = a.getInteger(R.styleable.IndexBezierView_minFontSize, mMinFontSize);
            mMaxFontSize = a.getInteger(R.styleable.IndexBezierView_maxFontSize, mMaxFontSize);
            mTipFontSize = a.getInteger(R.styleable.IndexBezierView_tipFontSize, mTipFontSize);
            mMaxBezierHeight = a.getDimension(R.styleable.IndexBezierView_maxBezierHeight, mMaxBezierHeight);
            mMaxBezierWidth = a.getDimension(R.styleable.IndexBezierView_maxBezierWidth, mMaxBezierWidth);
            mMaxBezierLines = a.getInteger(R.styleable.IndexBezierView_maxBezierLines, mMaxBezierLines);
            mAdditionalTipOffset = a.getDimension(R.styleable.IndexBezierView_additionalTipOffset, mAdditionalTipOffset);
            isRecoverColor = a.getBoolean(R.styleable.IndexBezierView_isRecoverColor, isRecoverColor);
            a.recycle();
        }

        mScroller = new Scroller(getContext());
        mTouch.x = 0;
        mTouch.y = -10 * mMaxBezierWidth;

        mBezier1 = new PointF[mMaxBezierLines];
        mBezier2 = new PointF[mMaxBezierLines];

        commonData(0, 0);
    }

    /**
     * 需 注意的是，传值单位是sp
     *
     * @param top    距离顶部的距离
     * @param bottom 距离底部的距离
     */
    private void commonData(int top, int bottom) {
        paddingTop = DensityUtil.dp2px(top);
        paddingBottom = DensityUtil.dp2px(bottom);
        mConLength = mData.length;
        mLastOffset = new float[mConLength];
        calculateBezierPoints();
    }

    public void setConstChar(String[] constChar, int top, int bottom) {
        this.mData = constChar;
        commonData(top, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 控件宽高
        int height = getHeight() - paddingTop - paddingBottom;
        int width = getWidth();

        // 单个字母高度
        float singleHeight = height / (float) mData.length;

        int workHeight = paddingTop;

        if (mAlpha == 0)
            return;

        //恢复画笔的默认设置。
        mPaint.reset();

        /**
         * 遍历所以字母内容
         */
        for (int i = 0; i < mConLength; i++) {

            mPaint.setColor(mNormalTextColor);
            mPaint.setAntiAlias(true);

            float xPos = width - mWidthOffset;           // 字母在 x 轴的位置      基本保持不变
            float yPos = workHeight + singleHeight / 2;  //字母在 y 轴的位置     该值一直在变化

            // 根据当前字母y的位置计算得到字体大小
            int fontSize = adjustFontSize(i, yPos);
            mPaint.setTextSize(fontSize);
            mAlpha = 255 - fontSize * 4;
            //            mPaint.setAlpha(mAlpha);
            if (i == mChooseIndex) {
                mPaint.setColor(mPressedTextColor);
                LogUtil.i(mData[i]);
            }

            // 添加一个字母的高度
            workHeight += singleHeight;
            // 绘制字母
            drawTextInCenter(canvas, mData[i], xPos + ajustXPosAnimation(i, yPos), yPos);

            // 如果手指抬起
            if (isUp) {
                if (mListener != null) {
                    mListener.onTouchActionUp(mData[mChooseIndex]);
                }

                isUp = false;
            }
            mPaint.reset();
        }
    }

    /**
     * @param canvas  画板
     * @param string  被绘制的字母
     * @param xCenter 字母的中心x方向位置
     * @param yCenter 字母的中心y方向位置
     */
    private void drawTextInCenter(Canvas canvas, String string, float xCenter, float yCenter) {

        FontMetrics fm = mPaint.getFontMetrics();
        float fontHeight = mPaint.getFontSpacing();

        float drawY = yCenter + fontHeight / 2 - fm.descent;

        if (drawY < -fm.ascent - fm.descent)
            drawY = -fm.ascent - fm.descent;

        if (drawY > getHeight())
            drawY = getHeight();

        mPaint.setTextAlign(Align.CENTER);

        canvas.drawText(string, xCenter, drawY, mPaint);
    }

    private int adjustFontSize(int i, float yPos) {

        // 根据水平方向偏移量计算出一个放大的字号
        float adjustX = Math.abs(ajustXPosAnimation(i, yPos));
        int adjustSize = (int) ((mMaxFontSize - mMinFontSize) * adjustX / mMaxBezierHeight) + mMinFontSize;

        return adjustSize;
    }

    /**
     * x 方向的向左偏移量
     *
     * @param i    当前字母的索引
     * @param yPos y方向的初始位置  会变化
     * @return
     */
    private float ajustXPosAnimation(int i, float yPos) {

        float offset;
        if (this.mAnimating || this.mHideAnimation) {
            // 正在动画中或在做隐藏动画
            offset = mLastOffset[i];
            if (offset != 0.0f) {
                offset += this.mAnimationOffset;
                if (offset > 0)
                    offset = 0;
            }
        } else {

            // 根据当前字母y方向位置, 计算水平方向偏移量
            offset = adjustXPos(yPos);

            // 当前触摸的x方向位置
            float xPos = mTouch.x;

            float width = getWidth() - mWidthOffset;
            width = width - 60;

            // 字母绘制时向左偏移量 进行修正, offset需要是<=0的值
            if (offset != 0.0f && xPos > width) {
                offset += (xPos - width);
            }
            if (offset > 0) {
                offset = 0;
            }

            mLastOffset[i] = offset;
        }
        return offset;
    }

    private float adjustXPos(float yPos) {

        float dis = yPos - mTouch.y; // 字母y方向位置和触摸时y值坐标的差值, 距离越小, 得到的水平方向偏差越大
        if (dis > -mMaxBezierWidth && dis < mMaxBezierWidth) {
            // 在2个贝赛尔曲线宽度范围以内 (一个贝赛尔曲线宽度是指一个山峰的一边)

            // 第一段 曲线
            if (dis > mMaxBezierWidth / 4) {
                for (int i = mMaxBezierLines - 1; i > 0; i--) {
                    // 从下到上, 逐个计算

                    if (dis == -mBezier1[i].y) // 落在点上
                        return mBezier1[i].x;

                    // 如果距离dis落在两个贝塞尔曲线模拟点之间, 通过三角函数计算得到当前dis对应的x方向偏移量
                    if (dis > -mBezier1[i].y && dis < -mBezier1[i - 1].y) {
                        return (dis + mBezier1[i].y) * (mBezier1[i - 1].x - mBezier1[i].x) / (-mBezier1[i - 1].y + mBezier1[i].y) + mBezier1[i].x;
                    }
                }
                return mBezier1[0].x;
            }

            // 第三段 曲线, 和第一段曲线对称
            if (dis < -mMaxBezierWidth / 4) {
                for (int i = 0; i < mMaxBezierLines - 1; i++) {
                    // 从上到下

                    if (dis == mBezier1[i].y) // 落在点上
                        return mBezier1[i].x;

                    // 如果距离dis落在两个贝塞尔曲线模拟点之间, 通过三角函数计算得到当前dis对应的x方向偏移量
                    if (dis > mBezier1[i].y && dis < mBezier1[i + 1].y) {
                        return (dis - mBezier1[i].y) * (mBezier1[i + 1].x - mBezier1[i].x) / (mBezier1[i + 1].y - mBezier1[i].y) + mBezier1[i].x;
                    }
                }
                return mBezier1[mMaxBezierLines - 1].x;
            }

            // 第二段 峰顶曲线
            for (int i = 0; i < mMaxBezierLines - 1; i++) {

                if (dis == mBezier2[i].y)
                    return mBezier2[i].x;

                // 如果距离dis落在两个贝塞尔曲线模拟点之间, 通过三角函数计算得到当前dis对应的x方向偏移量
                if (dis > mBezier2[i].y && dis < mBezier2[i + 1].y) {
                    return (dis - mBezier2[i].y) * (mBezier2[i + 1].x - mBezier2[i].x) / (mBezier2[i + 1].y - mBezier2[i].y) + mBezier2[i].x;
                }
            }
            return mBezier2[mMaxBezierLines - 1].x;

        }

        return 0.0f;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldmChooseIndex = mChooseIndex;
        final OnTouchLetterChangedListener listener = mListener;
        /**
         * 计算除去paddingTop后，用户点击不同位置对应的字母索引
         */
        final int c = (int) ((y - paddingTop) / (getHeight() - paddingTop - paddingBottom) * mData.length);


        switch (action) {
            case MotionEvent.ACTION_DOWN:

                if (this.getWidth() > mWidthOffset) {
                    if (event.getX() < this.getWidth() - mWidthOffset)
                        return false;
                }

                if (y < paddingTop || c < 0 || y > getHeight() - paddingBottom) {
                    return false;
                }

                mHideWaitingHandler.removeMessages(1);

                mScroller.abortAnimation();
                mAnimating = false;
                mHideAnimation = false;
                mAlpha = 255;

                mTouch.x = event.getX();
                mTouch.y = event.getY();

                if (oldmChooseIndex != c) {
                    if (c > 0 && c < mData.length) {
                        if (listener != null) {
                            listener.onTouchLetterChanged(mData[c], c);
                        }

                        mChooseIndex = c;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mTouch.x = event.getX();
                mTouch.y = event.getY();
                invalidate();
                if (oldmChooseIndex != c) {

                    if (c >= 0 && c < mData.length) {
                        if (listener != null) {
                            listener.onTouchLetterChanged(mData[c], c);
                        }
                        mChooseIndex = c;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mTouch.x = event.getX();
                mTouch.y = event.getY();

                isUp = true;
                mScroller.startScroll(0, 0, (int) mMaxBezierHeight, 0, 2000);
                mAnimating = true;
                postInvalidate();
                break;

        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mAnimating) {
                float x = mScroller.getCurrX();
                mAnimationOffset = x;
            } else if (mHideAnimation) {
                mAlpha = 255 - (int) mScroller.getCurrX();
            }
            invalidate();
        } else if (mScroller.isFinished()) {
            if (mAnimating) {

                mHideWaitingHandler.sendEmptyMessage(1);
            } else if (mHideAnimation) {
                mHideAnimation = false;
                this.mChooseIndex = -1;
                mTouch.x = -10000;
                mTouch.y = -10000;
            }

        }
    }


    /**
     * 计算出所有贝塞尔曲线上的点
     * 个数为 mMaxBezierLines * 2 = 64
     */
    private void calculateBezierPoints() {

        PointF mStart = new PointF();   // 开始点
        PointF mEnd = new PointF();        // 结束点
        PointF mControl = new PointF(); // 控制点


        // 计算第一段红色部分 贝赛尔曲线的点
        // 开始点
        mStart.x = 0.0f;
        mStart.y = -mMaxBezierWidth;

        // 控制点
        mControl.x = 0.0f;
        mControl.y = -mMaxBezierWidth / 2;

        // 结束点
        mEnd.x = -mMaxBezierHeight / 2;
        mEnd.y = -mMaxBezierWidth / 4;

        mBezier1[0] = new PointF();
        mBezier1[mMaxBezierLines - 1] = new PointF();

        mBezier1[0].set(mStart);
        mBezier1[mMaxBezierLines - 1].set(mEnd);

        for (int i = 1; i < mMaxBezierLines - 1; i++) {

            mBezier1[i] = new PointF();

            mBezier1[i].x = calculateBezier(mStart.x, mEnd.x, mControl.x, i / (float) mMaxBezierLines);
            mBezier1[i].y = calculateBezier(mStart.y, mEnd.y, mControl.y, i / (float) mMaxBezierLines);

        }

        // 计算第二段蓝色部分 贝赛尔曲线的点
        mStart.y = -mMaxBezierWidth / 4;
        mStart.x = -mMaxBezierHeight / 2;

        mControl.y = 0.0f;
        mControl.x = -mMaxBezierHeight;

        mEnd.y = mMaxBezierWidth / 4;
        mEnd.x = -mMaxBezierHeight / 2;

        mBezier2[0] = new PointF();
        mBezier2[mMaxBezierLines - 1] = new PointF();

        mBezier2[0].set(mStart);
        mBezier2[mMaxBezierLines - 1].set(mEnd);

        for (int i = 1; i < mMaxBezierLines - 1; i++) {

            mBezier2[i] = new PointF();
            mBezier2[i].x = calculateBezier(mStart.x, mEnd.x, mControl.x, i / (float) mMaxBezierLines);
            mBezier2[i].y = calculateBezier(mStart.y, mEnd.y, mControl.y, i / (float) mMaxBezierLines);
        }
    }

    /**
     * 贝塞尔曲线核心算法
     *
     * @param start
     * @param end
     * @param control
     * @param val
     * @return 公式及动图, 维基百科: https://en.wikipedia.org/wiki/B%C3%A9zier_curve
     * 中文可参考此网站: http://blog.csdn.net/likendsl/article/details/7852658
     */
    private float calculateBezier(float start, float end, float control, float val) {

        float t = val;
        float s = 1 - t;

        float ret = start * s * s + 2 * control * s * t + end * t * t;

        return ret;
    }
}
