package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jingbin.cloudreader.R;

import java.io.File;

/**
 * Created by jingbin on 2016/12/28.
 */

public class ShareUtils {

    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.action_share)));
    }

    /**
     * 分享网络图片
     */
    public static void shareNetImage(Activity context, String url) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                //由文件得到uri
                Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/*");
                context.startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });
    }

    public static Uri getUriWithPath(Context context, String filepath, String authority) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //7.0以上的读取文件uri要用这种方式了 注意在manifests里注册
            return FileProvider.getUriForFile(context.getApplicationContext(), authority+ ".fileprovider", new File(filepath));
        } else {
            return Uri.fromFile(new File(filepath));
        }
    }
}
