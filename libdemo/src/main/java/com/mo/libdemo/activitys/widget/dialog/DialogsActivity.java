package com.mo.libdemo.activitys.widget.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mo.lib.action.TitleBarAction;
import com.mo.lib.base.adapter.BaseRvAdapter;
import com.mo.lib.base.ui.BaseActivity;
import com.mo.lib.utils.hardware_utils.CameraHelper;
import com.mo.lib.view.dialog.BaseDialog;
import com.mo.lib.view.dialog.BaseIosDialog;
import com.mo.lib.view.dialog.CarmeraDialog;
import com.mo.lib.view.dialog.DialogCallBack;
import com.mo.lib.view.dialog.LoadingDialog;
import com.mo.lib.view.dialog.PhoneTakeDialog;
import com.mo.lib.view.recycle_view.KRecycleView;
import com.mo.libdemo.action.ListAction;
import com.mo.sc.libdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：Dialog样式
 */
@Route(path = "/libdemo/dialog")
public class DialogsActivity extends BaseActivity implements TitleBarAction, ListAction<String> {


    @Override
    protected int getLayoutId() {
        return R.layout.ld_activity_main;
    }

    @Override
    protected void initView() {
        setTitle("Dialog样式");
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<String> getList() {
        List<String> list1 = new ArrayList<>();
        list1.add("BaseDialog");
        list1.add("PhoneTakeDialog");
        list1.add("CarmeraDialog");
        list1.add("LoadingDialog");
        list1.add("BaseIosDialog");
        return list1;
    }

    @Override
    public KRecycleView getRecycleView() {
        return findViewById(R.id.krv_main);
    }

    @Override
    public BaseQuickAdapter getWrapRecyclerAdapter() {
        return new BaseRvAdapter<String>(R.layout.item_list, getList()) {
            @Override
            protected void doWhat(BaseViewHolder holder, String bean) {
                holder.setText(R.id.tv_item_list, bean);

            }
        }.setOnItemClickListener2(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == 0) {
                    new BaseDialog(getActivity()) {
                        @Override
                        protected int getLayoutId() {
                            return R.layout.base_list_layout;
                        }

                        @Override
                        protected void doWhat(Dialog dialog, View view) {
                            KRecycleView krv_base_list_layout = view.findViewById(R.id.krv_base_list_layout);
                            List<String> list = new ArrayList<>();
                            list.add("关闭方式=api");
                            list.add("关闭方式=返回键");
                            list.add("关闭方式=返回键/空白处");
                            list.add("屏宽占比0.4");
                            list.add("屏宽占比0.8");
                            list.add("阴影透明度0.4f");
                            list.add("阴影透明度0.8f");
                            krv_base_list_layout.setAdapter(new BaseRvAdapter<String>(R.layout.item_list, list) {
                                @Override
                                protected void doWhat(BaseViewHolder holder, String bean) {
                                    TextView tv_item_list = holder.getView(R.id.tv_item_list);
                                    tv_item_list.setText(bean);
                                }
                            }.setOnItemClickListener2(new OnItemClickListener() {
                                @Override
                                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                    if (position == 0) {
                                        showToast("调api关闭");
                                        dialog.dismiss();
                                    } else if (position == 1) {
                                        showToast("点击返回键可关闭");
                                        setDismissType(DismissType.BACK);
                                    } else if (position == 2) {
                                        showToast("点击返回键或内容空白处均可关闭");
                                        setDismissType(DismissType.OTHER);
                                    } else if (position == 3) {
                                        setDialogScale(0.4);
                                    } else if (position == 4) {
                                        setDialogScale(0.8);
                                    } else if (position == 5) {
                                        setDimAmount(0.4);
                                    } else if (position == 6) {
                                        setDimAmount(0.8);
                                    }
                                }
                            }));
                        }
                    }.setDialogScale(0.8).setDialogListener(new DialogCallBack() {
                        @Override
                        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                            showToast("点击了系统返回键");
                            return super.onKey(dialogInterface, i, keyEvent);
                        }

                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            super.onShow(dialogInterface);
                            showToast("弹窗显示了");
                        }

                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            super.onCancel(dialogInterface);
                            showToast("弹窗取消");
                        }

                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            super.onDismiss(dialogInterface);
                            showToast("弹窗消失");
                        }
                    }).show();
                } else if (position == 1) {
                    new PhoneTakeDialog(getActivity()) {
                        @Override
                        protected void actionPhoneChoose() {
                            showToast("去相册选图片");
                        }

                        @Override
                        protected void actionPhoneTake() {
                            showToast("去拍照");
                        }
                    }.show();
                } else if (position == 2) {
                    new CarmeraDialog(getActivity(), new CameraHelper.OnTakePic() {
                        @Override
                        public void getTakeResult(boolean isSave, File file, String path, Bitmap bitmap) {
                            showToast(path);
                        }
                    }).show();
                } else if (position == 3) {
                    new LoadingDialog(getActivity(), "你就当我现在正在跑接口吧").setDialogScale(0.8).setDismissType(BaseDialog.DismissType.BACK)
                            .show();
                } else if (position == 4) {
                    new BaseIosDialog(getActivity()).setTitle("重要通知").setMsg("江南皮革厂倒闭了")
                            .setLeftTextView("叫好", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showToast("撒花");
                                }
                            })
                            .setRightTextView("滚", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    showToast("。。。。 ");
                                }
                            })
                            .show();
                }
            }
        });
    }
}
