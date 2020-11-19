package com.mo.libdemo.modle;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.utils.beng_utils.BengUtil;
import com.mo.libdemo.bean.MainBean;
import com.mo.sc.libdemo.R;

import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:19
 * @ 功能：
 */
public class AdapterModle {
    public static BaseQuickAdapter getMainAdapter(final Activity mActivity, List<MainBean> list) {
        return new BaseRvAdapter<MainBean>(R.layout.ld_item_main,list){

            @Override
            protected void doWhat(BaseViewHolder holder, MainBean bean) {
                ImageView iv_item_main = holder.getView(R.id.iv_item_main);
                TextView tv_item_main = holder.getView(R.id.tv_item_main);
                tv_item_main.setText(bean.getTitle());
                iv_item_main.setImageResource(bean.getDwrableId());
            }
        }.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                BengUtil.getBuilder(mActivity, list.get(position).getActivity(), false).action();
            }
        });
//         return new KRecyclerAdapter(new BaseRecycleViewAdapter<MainBean>(mActivity, list) {
//            @Override
//            public void doWhat(KRecycleViewHolder holder, MainBean bean, int position, int itemViewType, RecyclerView mRecyclerView) {
//                ImageView iv_item_main = holder.getView(R.id.iv_item_main);
//                TextView tv_item_main = holder.getView(R.id.tv_item_main);
//                tv_item_main.setText(bean.getTitle());
//                iv_item_main.setImageResource(bean.getDwrableId());
//                holder.setItemClick(new KOnItemClickListenerImpl() {
//                    @Override
//                    public void onItemClick(View view, int postion) {
//                        BengUtil.getBuilder(mActivity, bean.getActivity(), false).action();
//                    }
//                });
//            }
//
//            @Override
//            public int getItemLayout(int viewType) {
//                return R.layout.ld_item_main;
//            }
//        }) {
//            @Override
//            public View getEmptyView() {
//                View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_main3, null, false);
//                inflate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                });
//                return inflate;
//            }
//        };

    }

}
