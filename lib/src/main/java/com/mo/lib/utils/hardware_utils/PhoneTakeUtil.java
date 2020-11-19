package com.mo.lib.utils.hardware_utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.mo.lib.modle.constants.ConstansePermissionGroup;
import com.mo.lib.modle.constants.KConstans;
import com.mo.lib.utils.dataUtil.date.DateUtil;
import com.mo.lib.utils.dataUtil.stringUtils.StringUtil;
import com.mo.lib.utils.file_utils.PathUtil;
import com.mo.lib.utils.image.BitmapUtil;
import com.mo.lib.utils.systemUtils.storageUtil.SDCardUtil;
import com.mo.lib.utils.tips_utils.LogUtil;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @ author：mo
 * @ data：2020/9/28:10:36
 * @ 功能：
 *   new PhoneTakeDialog(getActivity()) {
 *             protected void actionPhoneChoose() {
 *                 PhoneTakeUtil.actionMediaChoosePic(getActivity());
 *             }
 *
 *             protected void actionPhoneTake() {
 *                 PhoneTakeUtil.actionMediaTakePic(getActivity());
 *             }
 *         }.show();
 *
 *
 *     public void onActivityResult(int requestCode, int resultCode, Intent data) {
 *         super.onActivityResult(requestCode, resultCode, data);
 *         PhoneTakeUtil.PicResultBean bean = PhoneTakeUtil.onActivityResult(requestCode, resultCode, data,true);
 *         if (bean != null) {
 *             iv_user_info.setImageBitmap(BitmapUtil.getBitmap2(bean.getPath()));
 *             LogUtil.i(bean.toString());
 *         }
 *     }
 *
 */
public class PhoneTakeUtil {
    /** 开启拍照*/
    public static void actionMediaTakePic(Activity activity) {
        if (CameraUtil.isExistCamera()) {
            XXPermissions.with(activity).permission(ConstansePermissionGroup.PERMISSIONS_CAMERA).request(new OnPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean all) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(intent, KConstans.MEDIA_TAKE_PIC);
                }
                @Override
                public void noPermission(List<String> denied, boolean never) {
                    LogUtil.i("AAAAAAA");
                }
            });

        } else {
            ToastUtils.show("没有找到拍照设备！");
        }
    }

    /** 相册选图片*/
    public static void actionMediaChoosePic(Activity activity) {
        if (SDCardUtil.isEnable()) {
            XXPermissions.with(activity).permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).request(new OnPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean all) {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    activity.startActivityForResult(intent, KConstans.MEDIA_CHOOSE_PIC);
                }

                @Override
                public void noPermission(List<String> denied, boolean never) {
                    LogUtil.i("AAAAAAA");
                }
            });

        } else {
            ToastUtils.show("SD卡不可用");
        }
    }

    /** 图片行为结果处理*/
    public static PicResultBean onActivityResult(int requestCode, int resultCode, Intent data) {
        return onActivityResult(requestCode, resultCode, data, false);
    }
    /** 图片行为结果处理--是否保存图片*/
    public static PicResultBean onActivityResult(int requestCode, int resultCode, Intent data, Boolean isTakeSave) {
        return onActivityResult(requestCode, resultCode, data, isTakeSave, "");
    }
    /** 图片行为结果处理--是否保存图片并使用自定义路径*/
    public static PicResultBean onActivityResult(int requestCode, int resultCode, Intent data, Boolean isTakeSave, String takePath) {
        if (resultCode == RESULT_OK) {
            if (requestCode == KConstans.MEDIA_CHOOSE_PIC) {
                String imagePath = PathUtil.getPathByUri(data);
                return new PicResultBean(requestCode, resultCode, imagePath, BitmapUtil.getBitmapForRotate(imagePath), data);
            } else if (requestCode == KConstans.MEDIA_TAKE_PIC) {
                Bitmap bitmap = BitmapUtil.getBitmap(data);
                boolean saveResult = false;
                if (isTakeSave) {
                    String path = StringUtil.isEmpty(takePath) ? PathUtil.getExternalCacheDir() + DateUtil.getMS() + ".jpg" : takePath;
                    saveResult = BitmapUtil.saveFile(path, bitmap);
                    return new PicResultBean(requestCode, resultCode, saveResult ? path : "", bitmap, data);
                }else {
                    return new PicResultBean(requestCode, resultCode, "", bitmap, data);
                }

            }
        }
        return null;
    }

    /**
     * 图片结果实体
     */
    public static class PicResultBean {
        private int requestCode;
        private int resultCode;
        private String path;
        private Bitmap bitmap;
        private Intent data;

        @Override
        public String toString() {
            return "PicResultBean{" +
                    "requestCode=" + requestCode +
                    ", resultCode=" + resultCode +
                    ", path='" + path + '\'' +
                    ", bitmap=" + bitmap +
                    ", data=" + data +
                    '}';
        }

        public int getRequestCode() {
            return requestCode;
        }

        public void setRequestCode(int requestCode) {
            this.requestCode = requestCode;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Intent getData() {
            return data;
        }

        public void setData(Intent data) {
            this.data = data;
        }

        public PicResultBean(int requestCode, int resultCode, String path, Bitmap bitmap, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.path = path;
            this.bitmap = bitmap;
            this.data = data;
        }
    }
}
