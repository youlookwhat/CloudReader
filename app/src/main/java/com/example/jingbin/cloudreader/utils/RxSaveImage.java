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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.jingbin.cloudreader.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 保存图片，重复插入图片提示已存在
 *
 * @author jingbin
 */
public class RxSaveImage {

    private static Observable<Uri> saveImageAndGetPathObservable(Activity context, String url, String title) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                // 检查路径
                if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title)) {
                    subscriber.onError(new Exception("请检查图片路径"));
                }
                // 检查图片是否已存在
                File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
                if (appDir.exists()) {
                    String fileName = title.replace('/', '-') + ".jpg";
                    File file = new File(appDir, fileName);
                    if (file.exists()) {
                        subscriber.onError(new Exception("图片已存在"));
                    }
                }
                // 获得Bitmap
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context)
                            .load(url)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                } catch (Exception e) {
                    subscriber.onError(e);
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载到图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).flatMap(bitmap -> {
            File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName = title.replace('/', '-') + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                assert bitmap != null;
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(scannerIntent);
            return Observable.just(uri);
        }).subscribeOn(Schedulers.io());
    }


    public static void saveImageToGallery(Activity context, String mImageUrl, String mImageTitle) {
        // @formatter:off
        Subscription s = RxSaveImage.saveImageAndGetPathObservable(context, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "云阅相册");
                    String msg = String.format(CommonUtils.getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    ToastUtil.showToastLong(msg);
                }, error -> ToastUtil.showToastLong(error.getMessage()));
        // @formatter:on
//        addSubscription(s);
    }

}
