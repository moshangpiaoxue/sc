package com.mo.lib.view.viewpage;

/**
 * @ author：mo
 * @ data：2020/10/27:10:01
 * @ 功能：
 */

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * Created by pangli on 2018/12/13 9:43
 * 备注：RecyclerView实现类似ViewPager的PageChangeListener监听
 */
public class VpRecyclerChangeListenerHelper extends RecyclerView.OnScrollListener {
    private SnapHelper snapHelper;
    private OnPageChangeListener onPageChangeListener;
    private int oldPosition = -1;//防止同一Position多次触发

    public VpRecyclerChangeListenerHelper(SnapHelper snapHelper, OnPageChangeListener onPageChangeListener) {
        this.snapHelper = snapHelper;
        this.onPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrolled(recyclerView, dx, dy);
        }
    }

    public void setOldPosition() {
        this.oldPosition = -1;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int position = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //获取当前选中的itemView
        View view = snapHelper.findSnapView(layoutManager);
        if (view != null) {
            //获取itemView的position
            position = layoutManager.getPosition(view);
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrollStateChanged(recyclerView, newState);
            //newState == RecyclerView.SCROLL_STATE_IDLE 当滚动停止时触发防止在滚动过程中不停触发
            if (newState == RecyclerView.SCROLL_STATE_IDLE && oldPosition != position) {
                oldPosition = position;
                onPageChangeListener.onPageSelected(position);
            }
        }
    }

    public interface OnPageChangeListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);

        void onPageSelected(int position);
    }

    public abstract static class OnPageChangeListenerImpl implements OnPageChangeListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

    }
}

