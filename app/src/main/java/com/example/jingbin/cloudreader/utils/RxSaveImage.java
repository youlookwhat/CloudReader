/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.jingbin.cloudreader.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.jingbin.cloudreader.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 保存图片，重复插入图片提示已存在
 *
 * @author jingbin
 */
public class RxSaveImage {

    private static Observable<String> saveImageAndGetPathObservable(Activity context, String url, String title) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 检查路径
                if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title)) {
                    emitter.onError(new Exception("请检查图片路径"));
                }
                // 检查图片是否已存在
                File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
                if (appDir.exists()) {
                    File file = new File(appDir, getFileName(url, title));
                    if (file.exists()) {
                        emitter.onError(new Exception("图片已存在"));
                    }
                }
                // 没有目录创建目录
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                File file = new File(appDir, getFileName(url, title));

                try {
                    // 下载
                    File fileDo = Glide.with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    if (fileDo != null) {
                        // 复制图片
                        copyFile(fileDo.getAbsolutePath(), file.getPath());

                        // 通知图库更新
                        Uri uri = Uri.fromFile(file);
                        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                        context.sendBroadcast(scannerIntent);
                    } else {
                        emitter.onError(new Exception("无法下载到图片"));
                    }

                } catch (Exception e) {
                    emitter.onError(e);
                }
                emitter.onNext("");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }


    @SuppressLint("CheckResult")
    public static void saveImageToGallery(Activity context, String mImageUrl, String mImageTitle) {
        ToastUtil.showToast("开始下载图片");
        // @formatter:off
        RxSaveImage.saveImageAndGetPathObservable(context, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
                    String msg = String.format(CommonUtils.getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    ToastUtil.showToastLong(msg);
                }, error -> ToastUtil.showToastLong(error.getMessage()));
    }

    /**
     * gif动态图以对应后缀结尾
     */
    private static String getFileName(String mImageUrl, String mImageTitle) {
        String fileName;
        if (mImageUrl.contains(".gif")) {
            fileName = mImageTitle.replaceAll("/", "-") + ".gif";
        } else {
            fileName = mImageTitle.replaceAll("/", "-") + ".jpg";
        }
        return fileName;
    }

    /**
     * 复制文件
     */
    private static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 注意开权限
     */
    public static void saveToLocal(Context context, Bitmap bitmap) {
        try {
            File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
            // 没有目录创建目录
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File file = new File(appDir, "view_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream out;
            try {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                    out.flush();
                    out.close();
                    // 通知图库更新
                    Uri uri = Uri.fromFile(file);
                    Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                    context.sendBroadcast(scannerIntent);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成单个view的bitmap，
     */
    public static Bitmap createViewBitmap(View v) {
        if (v == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 周边边透明
        canvas.drawColor(Color.TRANSPARENT);
        v.draw(canvas);
        return bitmap;
    }
}
