package com.mo.libdemo.activitys.widget.popwindow;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.view.popupWindow.HorizontalPosition;
import com.mo.lib.view.popupWindow.KPopHelper;
import com.mo.lib.view.popupWindow.KPopupWindow;
import com.mo.lib.view.popupWindow.VerticalPosition;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.sc.libdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：PopWindow相关
 */
@Route(path = "/libdemo/pop")
public class PopWindowsActivity extends BaseActivity implements TitleBarAction, ListAction<PopWindowsActivity.PopBean> {
    private ImageView iv_pop_tag;
    private RelativeLayout rl_pop;

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_popwindow;
    }

    @Override
    protected void initView() {
        setTitle("PopWindow相关");
        getRecycleView().setLayoutGrid(5);
        iv_pop_tag = findViewById(R.id.iv_pop_tag);
        rl_pop = findViewById(R.id.rl_pop);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<PopBean> getList() {
        List<PopBean> list = new ArrayList<>();

        list.add(new PopBean("外上靠左", VerticalPosition.ABOVE, HorizontalPosition.LEFT));
        list.add(new PopBean("外上左对齐", VerticalPosition.ABOVE, HorizontalPosition.ALIGN_LEFT));
        list.add(new PopBean("外上居中", VerticalPosition.ABOVE, HorizontalPosition.CENTER));
        list.add(new PopBean("外上右对齐", VerticalPosition.ABOVE, HorizontalPosition.ALIGN_RIGHT));
        list.add(new PopBean("外上靠右", VerticalPosition.ABOVE, HorizontalPosition.RIGHT));

        list.add(new PopBean("外下靠左", VerticalPosition.BELOW, HorizontalPosition.LEFT));
        list.add(new PopBean("外下左对齐", VerticalPosition.BELOW, HorizontalPosition.ALIGN_LEFT));
        list.add(new PopBean("外下居中", VerticalPosition.BELOW, HorizontalPosition.CENTER));
        list.add(new PopBean("外下右对齐", VerticalPosition.BELOW, HorizontalPosition.ALIGN_RIGHT));
        list.add(new PopBean("外下靠右", VerticalPosition.BELOW, HorizontalPosition.RIGHT));

        list.add(new PopBean("内上靠左", VerticalPosition.ALIGN_TOP, HorizontalPosition.LEFT));
        list.add(new PopBean("内上左对齐", VerticalPosition.ALIGN_TOP, HorizontalPosition.ALIGN_LEFT));
        list.add(new PopBean("内上居中", VerticalPosition.ALIGN_TOP, HorizontalPosition.CENTER));
        list.add(new PopBean("内上右对齐", VerticalPosition.ALIGN_TOP, HorizontalPosition.ALIGN_RIGHT));
        list.add(new PopBean("内上靠右", VerticalPosition.ALIGN_TOP, HorizontalPosition.RIGHT));

        list.add(new PopBean("内下靠左", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.LEFT));
        list.add(new PopBean("内下左对齐", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.ALIGN_LEFT));
        list.add(new PopBean("内下居中", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.CENTER));
        list.add(new PopBean("内下右对齐", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.ALIGN_RIGHT));
        list.add(new PopBean("内下靠右", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.RIGHT));

        list.add(new PopBean("居中靠左", VerticalPosition.CENTER, HorizontalPosition.LEFT));
        list.add(new PopBean("居中左对齐", VerticalPosition.CENTER, HorizontalPosition.ALIGN_LEFT));
        list.add(new PopBean("居中居中", VerticalPosition.CENTER, HorizontalPosition.CENTER));
        list.add(new PopBean("居中右对齐", VerticalPosition.CENTER, HorizontalPosition.ALIGN_RIGHT));
        list.add(new PopBean("居中靠右", VerticalPosition.CENTER, HorizontalPosition.RIGHT));


        list.add(new PopBean("以上是", VerticalPosition.ABOVE, HorizontalPosition.LEFT));
        list.add(new PopBean("对子view", VerticalPosition.ABOVE, HorizontalPosition.LEFT));
        list.add(new PopBean("------", VerticalPosition.ABOVE, HorizontalPosition.LEFT));
        list.add(new PopBean("以下是", VerticalPosition.ABOVE, HorizontalPosition.LEFT));
        list.add(new PopBean("对父view", VerticalPosition.ABOVE, HorizontalPosition.LEFT));


        list.add(new PopBean("外上靠左", VerticalPosition.ABOVE, HorizontalPosition.LEFT, true));
        list.add(new PopBean("外上左对齐", VerticalPosition.ABOVE, HorizontalPosition.ALIGN_LEFT, true));
        list.add(new PopBean("外上居中", VerticalPosition.ABOVE, HorizontalPosition.CENTER, true));
        list.add(new PopBean("外上右对齐", VerticalPosition.ABOVE, HorizontalPosition.ALIGN_RIGHT, true));
        list.add(new PopBean("外上靠右", VerticalPosition.ABOVE, HorizontalPosition.RIGHT, true));

        list.add(new PopBean("外下靠左", VerticalPosition.BELOW, HorizontalPosition.LEFT, true));
        list.add(new PopBean("外下左对齐", VerticalPosition.BELOW, HorizontalPosition.ALIGN_LEFT, true));
        list.add(new PopBean("外下居中", VerticalPosition.BELOW, HorizontalPosition.CENTER, true));
        list.add(new PopBean("外下右对齐", VerticalPosition.BELOW, HorizontalPosition.ALIGN_RIGHT, true));
        list.add(new PopBean("外下靠右", VerticalPosition.BELOW, HorizontalPosition.RIGHT, true));

        list.add(new PopBean("内上靠左", VerticalPosition.ALIGN_TOP, HorizontalPosition.LEFT, true));
        list.add(new PopBean("内上左对齐", VerticalPosition.ALIGN_TOP, HorizontalPosition.ALIGN_LEFT, true));
        list.add(new PopBean("内上居中", VerticalPosition.ALIGN_TOP, HorizontalPosition.CENTER, true));
        list.add(new PopBean("内上右对齐", VerticalPosition.ALIGN_TOP, HorizontalPosition.ALIGN_RIGHT, true));
        list.add(new PopBean("内上靠右", VerticalPosition.ALIGN_TOP, HorizontalPosition.RIGHT, true));

        list.add(new PopBean("内下靠左", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.LEFT, true));
        list.add(new PopBean("内下左对齐", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.ALIGN_LEFT, true));
        list.add(new PopBean("内下居中", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.CENTER, true));
        list.add(new PopBean("内下右对齐", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.ALIGN_RIGHT, true));
        list.add(new PopBean("内下靠右", VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.RIGHT, true));

        list.add(new PopBean("居中靠左", VerticalPosition.CENTER, HorizontalPosition.LEFT, true));
        list.add(new PopBean("居中左对齐", VerticalPosition.CENTER, HorizontalPosition.ALIGN_LEFT, true));
        list.add(new PopBean("居中居中", VerticalPosition.CENTER, HorizontalPosition.CENTER, true));
        list.add(new PopBean("居中右对齐", VerticalPosition.CENTER, HorizontalPosition.ALIGN_RIGHT, true));
        list.add(new PopBean("居中靠右", VerticalPosition.CENTER, HorizontalPosition.RIGHT, true));


        return list;
    }

    @Override
    public KRecycleView getRecycleView() {
        return findViewById(R.id.krv_pop);
    }

    @Override
    public BaseQuickAdapter getWrapRecyclerAdapter() {
        return new BaseRvAdapter<PopBean>(R.layout.item_list, getList()) {
            @Override
            protected void doWhat(BaseViewHolder holder, PopBean bean) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_list);
                tv_item_tv.setText(bean.getName());
            }
        }.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                showPop(getList().get(position));
            }
        });
    }

    private void showPop(PopBean popBean) {
        new KPopHelper(getActivity())
                .setContentLayout(popBean.getName().equals("有编辑框") ? R.layout.item_et : R.layout.item_iv)
                .setAlpha(0.4f)
                .setAnimationStyle(KPopHelper.styles[0])
                .setListener(new KPopHelper.Listener() {
                    @Override
                    public void doWhat(KPopupWindow window, View contentView) {
                        contentView.findViewById(R.id.iv_item_iv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showToast("干啥");
                                window.dismiss();
                            }
                        });
                    }
                })
                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.i("sssssssss", "ffffffffffffffffffff");
                    }
                })
                .showAtAnchorView(popBean.isFull() ? rl_pop : iv_pop_tag, popBean.getVerticalPosition(), popBean.getHorizontalPosition(), popBean.isFull());
    }

    public class PopBean {
        private String name;
        private int verticalPosition;
        private int horizontalPosition;
        private boolean isFull = false;

        public PopBean(String name, int verticalPosition, int horizontalPosition) {
            this.name = name;
            this.verticalPosition = verticalPosition;
            this.horizontalPosition = horizontalPosition;
        }

        public PopBean(String name, int verticalPosition, int horizontalPosition, boolean isFull) {
            this.name = name;
            this.verticalPosition = verticalPosition;
            this.horizontalPosition = horizontalPosition;
            this.isFull = isFull;
        }

        public String getName() {
            return name;
        }

        public int getVerticalPosition() {
            return verticalPosition;
        }

        public int getHorizontalPosition() {
            return horizontalPosition;
        }

        public boolean isFull() {
            return isFull;
        }
    }
}
