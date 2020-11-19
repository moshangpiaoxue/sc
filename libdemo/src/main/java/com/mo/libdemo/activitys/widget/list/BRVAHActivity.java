package com.mo.libdemo.activitys.widget.list;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.base.adapter.BaseRvMultiAdapter;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.tips_utils.LogUtil;
import com.mo.lib.utils.viewUtil.ViewUtil;
import com.mo.lib.view.PullToRefresh.BaseRefreshListener;
import com.mo.lib.view.PullToRefresh.KPullToRefreshLayout;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.bean.ItemBean;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：RecycleView
 */
@Route(path = "/libdemo/brvah")
public class BRVAHActivity extends BaseActivity {
    private KPullToRefreshLayout kptrl_brvah;
    private KRecycleView krv_rv;
    private KRecycleView krv_brvah_bottom;
    List<ItemBean> data;
    /** 是否多布局 */
    private boolean isMulti = false;
    private BaseQuickAdapter adapter1;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_brvah;
    }

    @Override
    public void onClick(View v) {
        int i1 = v.getId();
        if (i1 == R.id.tv_brvah_dan) {
            isMulti = false;
        } else if (i1 == R.id.tv_brvah_duo) {
            isMulti = true;
        }
        initRv();
    }

    @Override
    protected void initView() {
        kptrl_brvah = findViewById(R.id.kptrl_brvah);
        krv_rv = findViewById(R.id.krv_brvah);
        krv_brvah_bottom = findViewById(R.id.krv_brvah_bottom);
        data = new ArrayList<>();
        kptrl_brvah.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("下完");
                        data.add(0, new ItemBean("下拉数据", 0));
                        krv_rv.getAdapter().notifyDataSetChanged();
                        kptrl_brvah.finishRefresh();
                    }
                }, 1000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("上完");
                        data.add(new ItemBean("上拉数据", 1));
                        krv_rv.getAdapter().notifyDataSetChanged();
                        kptrl_brvah.finishLoadMore();
                    }
                }, 1000);
            }
        });
        initBottom();
        initRv();
    }

    private void initRv() {
        if (krv_rv.getAdapter() != null) {
            krv_rv.setAdapter(null);
        }

        if (!isMulti) {
            krv_rv.setAdapter(adapter1 = new BaseRvAdapter<ItemBean>(R.layout.item_tv2, data) {

                @Override
                protected void doWhat(BaseViewHolder holder, ItemBean bean) {
                    holder.setText(R.id.tv_item_tv2, bean.getString());
                }

            }.setOnItemClickListener2(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    LogUtil.i(position + "==============" + data.get(position));
                }
            }));
        } else {
            krv_rv.setAdapter(adapter1 = new BaseRvMultiAdapter<ItemBean>(data) {

                @Override
                public void addItemTypes() {
                    addItemType(0, R.layout.item_tv);
                    addItemType(1, R.layout.item_tv2);
                }


                @Override
                protected void doWhat(BaseViewHolder holder, ItemBean bean, int itemViewType) {
                    LogUtil.i(itemViewType + "");
                    if (itemViewType == 0) {
                        holder.setText(R.id.tv_item_tv, bean.getString() + "  itemtype=" + itemViewType);
                    } else if (itemViewType == 1) {
                        holder.setText(R.id.tv_item_tv2, bean.getString() + "  itemtype=" + itemViewType);
                    }
                }

            }.setOnItemClickListener2(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    LogUtil.i(position + "==============" + data.get(position));
                }
            }));
        }
        adapter1.setEmptyView(ViewUtil.getView(getActivity(), R.layout.base_loading));
    }

    private void initBottom() {
        List<String> list = new ArrayList<>();
        list.add("数据加载中");
        list.add("数据空");
        list.add("数据异常");
        list.add("数据正常");
        list.add("添加头");
        list.add("添加尾");
        list.add("异常显示头尾");
        list.add("异常隐藏头尾");
        list.add("自动换行");
        list.add("垂直布局");
        list.add("可下拉");
        list.add("可上拉");
        list.add("不可下拉");
        list.add("不可上拉");
        krv_brvah_bottom.setLayoutGrid(3);
        krv_brvah_bottom.setAdapter(new BaseRvAdapter<String>(R.layout.item_tv2, list) {

            @Override
            protected void doWhat(BaseViewHolder holder, String bean) {
                holder.setText(R.id.tv_item_tv2, bean);
            }

        }.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == 0) {
                    adapter1.setEmptyView(ViewUtil.getView(getActivity(), R.layout.base_loading));
                    data.clear();
                    adapter1.notifyDataSetChanged();
                } else if (position == 1) {
                    adapter1.setEmptyView(ViewUtil.getView(getActivity(), R.layout.base_empty));
                    data.clear();
                    adapter1.notifyDataSetChanged();
                } else if (position == 2) {
                    adapter1.setEmptyView(ViewUtil.getView(getActivity(), R.layout.base_error));
                    data.clear();
                    adapter1.notifyDataSetChanged();
                } else if (position == 3) {
                    //                    for (int i = 0; i < 5; i++) {
                    data.add(new ItemBean("普通                                                                                                           普通", 0));
                    data.add(new ItemBean("普通普通", 0));
                    data.add(new ItemBean("普通  普通", 0));
                    data.add(new ItemBean("普通             普通", 1));
                    data.add(new ItemBean("普通   普通", 0));
                    data.add(new ItemBean("普通                         普通", 0));
                    data.add(new ItemBean("普通 普通", 0));
                    //                    }
                    adapter1.notifyDataSetChanged();
                } else if (position == 4) {
                    adapter1.addHeaderView(ViewUtil.getView(getActivity(), R.layout.item_main2));
                } else if (position == 5) {
                    adapter1.addFooterView(ViewUtil.getView(getActivity(), R.layout.item_main3));
                } else if (position == 6) {
                    adapter1.setHeaderWithEmptyEnable(true);
                    adapter1.setFooterWithEmptyEnable(true);
                    adapter1.notifyDataSetChanged();
                } else if (position == 7) {
                    adapter1.setHeaderWithEmptyEnable(false);
                    adapter1.setFooterWithEmptyEnable(false);
                    adapter1.notifyDataSetChanged();
                } else if (position == 8) {
                    krv_rv.setFlowLayoutManager();
                } else if (position == 9) {
                    krv_rv.setLayoutLinerVertical();
                } else if (position == 10) {
                    kptrl_brvah.setCanRefresh(true);
                } else if (position == 11) {
                    kptrl_brvah.setCanLoadMore(true);
                } else if (position == 12) {
                    kptrl_brvah.setCanRefresh(false);
                } else if (position == 13) {
                    kptrl_brvah.setCanLoadMore(false);
                }
            }
        }));

    }

    @Override
    protected void initData() {

    }

}
