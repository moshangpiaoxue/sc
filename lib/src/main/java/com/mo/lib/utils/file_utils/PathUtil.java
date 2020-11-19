package com.mo.lib.utils.file_utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.RequiresPermission;


import com.mo.lib.k;

import java.io.File;
import java.util.Objects;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * @ author：mo
 * @ data：2020/7/2:15:46
 * @ 功能：
 */
public class PathUtil {
    /**
     * 格式化文件路径
     * 示例：  传入 "sloop" "/sloop" "sloop/" "/sloop/"
     * 返回 "/sloop"
     */
    private static String formatPath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        while (path.endsWith("/")) {
            path = new String(path.toCharArray(), 0, path.length() - 1);
        }
        return path;
    }

    //////////////////////////////////////系统文件目录//////////////////////////////////////////////////////////

    /** 程序系统文件目录*/
    public static String getFileDir() {
        return String.valueOf(k.app().getFilesDir());
    }

    /** 程序系统文件目录*/
    public static String getFileDir( String customPath) {
        String path = k.app().getFilesDir() + formatPath(customPath);
        FileUtil.mkdir(path);
        return path;
    }
    //////////////////////////////////////系统缓存目录//////////////////////////////////////////////////////////

    /**
     * @return 程序系统缓存目录
     */
    public static String getCacheDir() {
        return String.valueOf(k.app().getCacheDir());
    }

    /**
     * @param customPath 自定义路径
     * @return 程序系统缓存目录
     */
    public static String getCacheDir( String customPath) {
        String path = k.app().getCacheDir() + formatPath(customPath);
        FileUtil.mkdir(path);
        return path;
    }

    //////////////////////////////////////Sdcard文件目录//////////////////////////////////////////////////////////

    /**
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir() {
        return String.valueOf(k.app().getExternalFilesDir(""));
    }

    /**
     * @param customPath 自定义路径
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir( String customPath) {
        String path = k.app().getExternalFilesDir("") + formatPath(customPath);
        FileUtil.mkdir(path);
        return path;
    }

    //////////////////////////////////////Sdcard缓存目录//////////////////////////////////////////////////////////

    /**
     * @return 内存卡缓存目录 SDCard/Android/data/应用包名/cache/目录，一般存放临时缓存数据据
     */
    public static String getExternalCacheDir() {
        return k.app().getExternalCacheDir().getAbsolutePath() + File.separator;
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context, String customPath) {
        String path = context.getExternalCacheDir() + formatPath(customPath);
        FileUtil. mkdir(path);
        return path;
    }


    //////////////////////////////////////公共文件夹//////////////////////////////////////////////////////////

    /**
     * @return 公共下载文件夹
     */
    public static String getPublicDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }








    /**
     * 获取外部储存中以 APP 包名命名的文件夹路径
     */
    public static String getPackageDir() {
        return Environment.getExternalStorageDirectory() + "/" + k.app().getPackageName() + "/";
    }

    /** 获取路径==SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据 */
    public static String getExternalFilesDir(String type) {
        return Objects.requireNonNull(k.app().getExternalFilesDir(type)).getAbsolutePath() + File.separator;
    }


    /** 根据intent获取文件绝对路径 */
    @SuppressLint("MissingPermission")
    public static String getPathByUri(Intent data) {
        if (data != null || data.getData() != null) {
            return getPathByUri(data.getData());
        } else {
            return null;
        }
    }

    /**
     * 根据uri获取文件的绝对路径，解决Android 4.4以上 根据uri获取路径的方法
     */
    @RequiresPermission(READ_EXTERNAL_STORAGE)
    public static String getPathByUri(Uri uri) {
        if (uri == null) return null;

        String path = null;
        String scheme = uri.getScheme();
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = k.app().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }

        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(k.app(), uri)) {
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(k.app(), contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(k.app(), contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(scheme)) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else if (isHuaWeiUri(uri)) {
                String uriPath = uri.getPath();
                //content://com.huawei.hidisk.fileprovider/root/storage/emulated/0/Android/data/com.xxx.xxx/
                if (uriPath != null && uriPath.startsWith("/root")) {
                    return uriPath.replaceAll("/root", "");
                }
            }
            return getDataColumn(k.app(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(scheme)) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * content://com.huawei.hidisk.fileprovider/root/storage/emulated/0/Android/data/com.xxx.xxx/
     *
     * @param uri
     * @return
     */
    public static boolean isHuaWeiUri(Uri uri) {
        return "com.huawei.hidisk.fileprovider".equals(uri.getAuthority());
    }


}
