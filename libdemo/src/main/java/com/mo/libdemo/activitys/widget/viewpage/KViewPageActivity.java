package com.mo.libdemo.activitys.widget.viewpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.base.adapter.KPageAdapter;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.lib.view.viewpage.KViewPage;
import com.mo.lib.view.viewpage.page_transforme.AlphaPageTransformer;
import com.mo.lib.view.viewpage.page_transforme.RotateDownPageTransformer;
import com.mo.lib.view.viewpage.page_transforme.RotateUpPageTransformer;
import com.mo.lib.view.viewpage.page_transforme.RotateYTransformer;
import com.mo.lib.view.viewpage.page_transforme.ScaleInTransformer;
import com.mo.libdemo.modle.ListModle;
import com.mo.sc.libdemo.R;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：KViewPage
 */
public class KViewPageActivity extends BaseActivity implements TitleBarAction {

    private KViewPage vp_vp;
    private KRecycleView krv_vp;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_kvp;
    }


    @Override
    protected void initView() {
        setTitle("KViewPage");

        vp_vp = findViewById(R.id.vp_vp);
        krv_vp = findViewById(R.id.krv_vp);
        vp_vp.setAdapter(new KPageAdapter<String>(getContext(), ListModle.getList(5), R.layout.item_main3) {
            @Override
            protected void doWhat(ViewGroup container, View view, int position) {

            }
        });
        krv_vp.setAdapter(new BaseRvAdapter<String>(R.layout.item_tv ,ListModle.getList(6)) {
            @Override
            protected void doWhat(BaseViewHolder holder, String bean) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                if (holder.getAdapterPosition() == 0) {
                    tv_item_tv.setText("切换滑动开关,当前状态=" + vp_vp.isCanScroll());
                } else if (holder.getAdapterPosition() == 1) {
                    tv_item_tv.setText("切换效果=" + "中间颜色重两边颜色浅");
                } else if (holder.getAdapterPosition() == 2) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以底部为基点倾斜");
                }else if (holder.getAdapterPosition() == 3) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以顶部为基点倾斜");
                }else if (holder.getAdapterPosition() == 4) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以Y轴为基点旋转");
                }else if (holder.getAdapterPosition() == 5) {
                    tv_item_tv.setText("切换效果=" + "中间大两边小");
                }
            }
        }.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == 0) {
                    vp_vp.setScanScroll(!vp_vp.isCanScroll());
                    adapter.notifyDataSetChanged();
//                    tv_item_tv.setText("切换滑动开关,当前状态=" + vp_vp.isCanScroll());
                } else if (position == 1) {
                    vp_vp.setPageTransformer(true, new AlphaPageTransformer());
                }else if (position == 2) {
                    vp_vp.setPageTransformer(true, new RotateDownPageTransformer());
                }else if (position == 3) {
                    vp_vp.setPageTransformer(true, new RotateUpPageTransformer());
                }else if (position == 4) {
                    vp_vp.setPageTransformer(true, new RotateYTransformer());
                }else if (position == 5) {
                    vp_vp.setPageTransformer(true, new ScaleInTransformer());
                }
            }
        }));
    }

    @Override
    protected void initData() {

    }

}
