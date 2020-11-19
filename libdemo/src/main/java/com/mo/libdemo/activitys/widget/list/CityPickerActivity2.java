package com.mo.libdemo.activitys.widget.list;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.XXPermissions;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.base.adapter.HoverRecycleViewAdapter;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.modle.bean.CityBean;
import com.mo.lib.utils.animator_util.tween.KRotateAnimationUtils;
import com.mo.lib.utils.app_utils.permissions_util.KOnPermission;
import com.mo.lib.utils.dataUtil.ListUtil;
import com.mo.lib.utils.systemUtils.LocationUtils;
import com.mo.lib.utils.systemUtils.VibratorUtil;
import com.mo.lib.utils.viewUtil.click.OnMultiClickListener;
import com.mo.lib.view.indext_view.IndexView;
import com.mo.lib.view.indext_view.IndexBezierView;
import com.mo.lib.view.recycle_view.BGADivider;
import com.mo.lib.view.recycle_view.BGARVVerticalScrollHelper;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.modle.ListModle;
import com.mo.sc.libdemo.R;

import java.util.List;


/**
 * @ author：mo
 * @ data：2020/8/12:15:31
 * @ 功能：
 */
public class CityPickerActivity2 extends BaseActivity {
    private KRecycleView mDataRv;
    private IndexView mIndexView;
    private IndexBezierView ll_sticky_letter;
    private TextView mTipTv;


    private HoverRecycleViewAdapter<CityBean> adapter;
    private BGARVVerticalScrollHelper mRecyclerViewScrollHelper;


    @Override
    protected int getLayoutId() {
        return R.layout.act_list_city_picker2;
    }

    @Override
    protected void initView() {
        mDataRv = findViewById(R.id.rv_sticky_data);
        mDataRv.setHasFixedSize(true);
        mDataRv.setNestedScrollingEnabled(false);
        mIndexView = findViewById(R.id.iv_sticky_index);
        ll_sticky_letter = findViewById(R.id.ll_sticky_letter);
        ll_sticky_letter.setOnTouchLetterChangedListener(new IndexBezierView.OnTouchLetterChangedListener() {
            @Override
            public void onTouchLetterChanged(String s, int index) {
//                LogUtil.i("==="+index+"=="+s);
//                int position = adapter.getc(s.charAt(0));
////                if (position != -1) {
//                    mRecyclerViewScrollHelper.smoothScrollToPosition(index+3);
////                }
                VibratorUtil.getOnce(100);
            }

            @Override
            public void onTouchActionUp(String s) {
            }
        });
        mTipTv = findViewById(R.id.tv_sticky_tip);
        mIndexView.setTipsView(mTipTv);
        XXPermissions.with((Activity) getContext()).permission(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.VIBRATE).request(new KOnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {
                if (all) {
                    mDataRv.setAdapter(adapter = new HoverRecycleViewAdapter<CityBean>( ListModle.loadIndexModelData()) {
                        @Override
                        public void addItemTypes() {
                            addItemType(0,R.layout.item_city_loction);
                            addItemType(1,R.layout.item_city_hot);
                            addItemType(2,R.layout.cp_item_city_listview2);
                        }

                        @Override
                        protected void doWhat(BaseViewHolder holder, CityBean bean, int itemViewType) {
                            if (holder.getItemViewType()==0) {
                                TextView tv_located_city = holder.getView(R.id.tv_located_city);
                                ImageView iv_chognxindingwei = holder.getView(R.id.iv_chognxindingwei);
                                holder.getView(R.id.tv_chognxindingwei).setOnClickListener(new OnMultiClickListener() {
                                    @Override
                                    public void onMultiClick(View v) {
                                        setLoctionView(tv_located_city, iv_chognxindingwei);
                                    }
                                });
                                setLoctionView(tv_located_city, iv_chognxindingwei);


                            } else if (holder.getItemViewType()==1) {
                                KRecycleView gridView = holder.getView(R.id.gridview_hot_city);
                                gridView.setLayoutGrid(4);
                                gridView.setAdapter(new BaseRvAdapter<CityBean>(R.layout.cp_item_hot_city_gridview, ListModle.getHotCity()) {
                                    @Override
                                    protected void doWhat(BaseViewHolder holder, CityBean bean) {
                                        TextView tv_hot_city_name = holder.getView(R.id.tv_hot_city_name);
                                        tv_hot_city_name.setText(bean.getName());
                                    }
                                });
                            } else {
                                TextView tv_item_index_city = holder.getView(R.id.tv_item_index_city);
                                tv_item_index_city.setText(bean.getName());
                            }
                        }

                        @Override
                        protected boolean isCategoryFistItem(int position, CityBean noeBean, CityBean beforeBean) {
                            String currentTopc = getData().get(position).getTopc();
                            String preTopc = getData().get(position - 1).getTopc();
                            // 当前条目的分类和上一个条目的分类不相等时，当前条目为该分类下的第一个条目
                            if (!TextUtils.equals(currentTopc, preTopc)) {
                                return true;
                            }
                            return false;
                        }

                        @Override
                        protected int getPositionForCategory(int category, int position, CityBean bean) {
                            String sortStr = bean.getTopc();
                            char firstChar = sortStr.toUpperCase().charAt(0);
                            if (firstChar == category) {
                                return position;
                            }
                            return -1;
                        }
                    });
                    initStickyDivider();
                }
            }
        });
    }

    private void initStickyDivider() {
        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {

            @Override
            protected boolean isCategoryFistItem(int position) {
                return adapter.isCategoryFistItem(position);
            }

            @Override
            protected String getCategoryName(int position) {
                return adapter.getData().get(position).getTopc();
            }

            @Override
            protected int getFirstVisibleItemPosition() {
                return mRecyclerViewScrollHelper.findFirstVisibleItemPosition();
            }
        };

        mDataRv.addItemDecoration(new BGADivider(stickyDelegate));
        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mDataRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }
        });
        mIndexView.setSelectedListener(new IndexView.IndexViewSelectedListener() {
            @Override
            public void onIndexViewSelected(IndexView indexView, String text) {
                int position = adapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });
        List<String> stringList = ListUtil.removeRepeat(ListModle.getToc());
        mIndexView.setData(stringList.toArray(new String[stringList.size()]));
    }

    @Override
    protected void initData() {
    }


    private void setLoctionView(TextView tv_located_city, ImageView iv_chognxindingwei) {
        tv_located_city.setText("定位中..");
        iv_chognxindingwei.startAnimation(KRotateAnimationUtils.getRotateAnimation());


        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //                tv_located_city.setText(LocationUtils.getCity());
                //                iv_chognxindingwei.clearAnimation();
                LocationUtils.getCtiy(new LocationUtils.CallBack() {
                    @Override
                    public void callBack(String city) {
                        tv_located_city.setText(city);
                        iv_chognxindingwei.clearAnimation();
                    }
                });
            }
        }, 1500);

    }

}
