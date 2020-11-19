package com.mo.lib.view.popupWindow;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ author：mo
 * @ data：2020/11/19:10:06
 * @ 功能：垂直方向上的位置
 */
@IntDef({
        VerticalPosition.CENTER,
        VerticalPosition.ABOVE,
        VerticalPosition.BELOW,
        VerticalPosition.ALIGN_TOP,
        VerticalPosition.ALIGN_BOTTOM,
})
@Retention(RetentionPolicy.SOURCE)
public @interface VerticalPosition {
    //居中
    int CENTER = 0;
    //上
    int ABOVE = 1;
    //下
    int BELOW = 2;
    //上对齐
    int ALIGN_TOP = 3;
    //下对齐
    int ALIGN_BOTTOM = 4;
}
