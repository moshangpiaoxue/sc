package com.mo.lib.view.popupWindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.mo.lib.R;
import com.mo.lib.utils.viewUtil.ViewUtil;

/**
 * @ author：mo
 * @ data：2020/11/19:9:25
 * @ 功能：popwindow封装
 * 注：1、自适应尺寸弹出时可能会与目标view有间距，注意查看目标view里的内间距设置，或者设置一个背景色再看
 * 2、当内容view的尺寸有具体值或者为最大时，展示位置可能有误，这是由于目标view和内容view计算占位策略造成的
 */
public class KPopHelper {
    /**
     * 动画出现样式集合 0=从上到下 1=从下到上
     */
    public static int[] styles = {R.style.TopAnimStyle, R.style.BottomAnimStyle};

    public interface Listener {
        void doWhat(KPopupWindow window, View contentView);
    }

    private Listener doWhat;
    private Context mContext;
    private KPopupWindow kPopupWindow;

    @SuppressLint("WrongConstant")
    public KPopHelper(Context mContext) {
        this.mContext = mContext;
        kPopupWindow = new KPopupWindow(mContext);
        //        setSize(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setAlpha(1f);
        setAnimationStyle(-1);
        setOutsideTouchDismiss(true);
    }

    public KPopHelper setListener(Listener doWhat) {
        this.doWhat = doWhat;
        return this;
    }

    public KPopHelper setContentView(View contentView) {
        kPopupWindow.setContentView(contentView);
        setSize(0, 0);
        if (doWhat != null) {
            doWhat.doWhat(kPopupWindow, contentView);
        }
        return this;
    }

    public KPopHelper setContentLayout(int layoutId) {
        setContentView(ViewUtil.getView(mContext, layoutId));
        return this;
    }

    public KPopHelper setSize(int width, int height) {
        if (width == 0 || height == 0) {
            kPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            kPopupWindow.setWidth(kPopupWindow.getContentView().getMeasuredWidth());
            kPopupWindow.setHeight(kPopupWindow.getContentView().getMeasuredHeight());
        } else {
            kPopupWindow.setWidth(width);
            kPopupWindow.setHeight(height);
        }
        return this;
    }


    public KPopHelper setAlpha(float alpha) {
        kPopupWindow.setAlpha(alpha);
        return this;
    }

    public KPopHelper setAnimationStyle(int mAnimationStyle) {
        if (mAnimationStyle != -1) {
            kPopupWindow.setAnimationStyle(mAnimationStyle);
        }
        return this;
    }

    public KPopHelper setOutsideTouchDismiss(boolean isTouchOutsideDismiss) {
        kPopupWindow.touchOutsideDismiss(isTouchOutsideDismiss);
        return this;
    }

    public KPopHelper setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        kPopupWindow.setOnDismissListener(onDismissListener);
        return this;
    }

    public KPopHelper showAtAnchorView(@NonNull View anchorView, @VerticalPosition int verticalPos, @HorizontalPosition int horizontalPos) {
        showAtAnchorView(anchorView, verticalPos, horizontalPos, true);
        return this;
    }

    public KPopHelper showAtAnchorView(@NonNull View anchorView, @VerticalPosition int verticalPos, @HorizontalPosition int horizontalPos, boolean fitInScreen) {
        showAtAnchorView(anchorView, verticalPos, horizontalPos, 0, 0, fitInScreen);
        return this;
    }

    public KPopHelper showAtAnchorView(@NonNull View anchorView, @VerticalPosition int verticalPos, @HorizontalPosition int horizontalPos, int x, int y) {
        showAtAnchorView(anchorView, verticalPos, horizontalPos, x, y, true);
        return this;
    }

    public KPopHelper showAtAnchorView(@NonNull View anchorView, @VerticalPosition int verticalPos, @HorizontalPosition int horizontalPos, int x, int y, boolean fitInScreen) {
        kPopupWindow.showAtAnchorView(anchorView, verticalPos, horizontalPos, x, y, fitInScreen);
        return this;
    }

    public void dismiss() {
        if (kPopupWindow != null) {
            kPopupWindow.dismiss();
        }

    }

}
