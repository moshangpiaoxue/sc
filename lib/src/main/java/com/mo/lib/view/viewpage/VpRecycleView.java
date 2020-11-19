package com.mo.lib.view.viewpage;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.mo.lib.view.recycle_view.KRecycleView;


/**
 * @ author：mo
 * @ data：2020/10/27:9:47
 * @ 功能：实现vp效果的rv
 */
public class VpRecycleView extends KRecycleView {
    private VpRecyclerChangeListenerHelper.OnPageChangeListener helper;
    private VpRecyclerChangeListenerHelper helper2;

    public VpRecycleView(Context context) {
        this(context, null);
    }

    public VpRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VpRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setViewPageType(int type, VpRecyclerChangeListenerHelper.OnPageChangeListener helper) {
        setLayoutLinerHorizontal();
        SnapHelper snapHelper;
        if (type == 0) {
            snapHelper = new LinearSnapHelper();
        } else {
            snapHelper = new PagerSnapHelper();
        }
        snapHelper.attachToRecyclerView(this);
        if (helper != null) {
            this.helper = helper;
            this.addOnScrollListener(helper2 = new VpRecyclerChangeListenerHelper(snapHelper, helper));
        }
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        if (helper != null) {
            helper2.setOldPosition();
            helper.onPageSelected(position);
        }
    }
}
