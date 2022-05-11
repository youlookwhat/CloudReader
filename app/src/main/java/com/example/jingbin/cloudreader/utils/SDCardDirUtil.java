package com.example.jingbin.cloudreader.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理SD上存储路径问题，统一保存的目录
 * 原则：只有保存的图片在外部，其他的都在内部删除应用能被删除
 * 从 Android 11 开始，应用无法在外部存储设备上创建自己的应用专用目录。如需访问系统为您的应用提供的目录，请调用 getExternalFilesDirs()
 * Android 11 时，getExternalStorageDirectory会在Pictures文件夹根目录下
 * <p>
 * 通过uri得到绝对路径 FilePathUtil.getFileAbsolutePath(this, uri);
 *
 * @author jingbin
 */
public class SDCardDirUtil {

    /**
     * 保存图片的相册名字
     */
    private static final String ClOUD_READER_PICTURE = "云阅相册";

    /**
     * android 11及以上保存图片到相册 不需要权限
     */
    public static void saveImageToGallery2(Activity context, Bitmap image, String ext, OnSaveListener listener) {
        long mImageTime = System.currentTimeMillis();
        String imageDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(mImageTime));
        String SCREENSHOT_FILE_NAME_TEMPLATE = "cloudreader_%s." + ext;//图片名称，以"mlxx"+时间戳命名
        String mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android11和12

            final ContentValues values = new ContentValues();
            // Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
            //  Environment.DIRECTORY_DCIM + "/Camera" 系统相册
            String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "云阅";
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
//        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/Camera");
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/" + ext);
            values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
            values.put(MediaStore.MediaColumns.DATE_EXPIRES, (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000);
            values.put(MediaStore.MediaColumns.IS_PENDING, 1);

            ContentResolver resolver = context.getContentResolver();
            final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            try {

//                if (outImageUri != null) {
//                    outputStream = Objects.requireNonNull(context.getContentResolver().openOutputStream(outImageUri));
//                    val u = URL(urlPath);
//                    inputStream = u.openStream();
//                    inBuffer = inputStream.source().buffer()
//                    val bufferCopy = MyFileUtils.bufferCopy(inBuffer, outputStream)
//                    if (bufferCopy) {
//                        var path = MyFileUtils.getPath(context, outImageUri)
//                        /*--------------通知相册广播-----------------------------*/
//                        // deprecated (4.4以后只有系统级应用才有使用广播通知系统扫描的权限)
//                        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
//                        intent.data = outImageUri
//                        context.sendBroadcast(intent)
//                        MediaScannerConnection.scanFile(context, arrayOf(path), null) { path, uri -> Log.e("scan file ", "$path---$uri") }
//                        /*--------------通知广播-----------------------------*/
//                        return path
//                    }
//                }


                // First, write the actual data for our screenshot
                try (OutputStream out = resolver.openOutputStream(uri)) {
                    // CompressFormat.PNG
                    if (!image.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                        if (listener != null) {
                            listener.onFailed();
                        }
//                    throw new IOException("Failed to compress");
                    }
                }
                // Everything went well above, publish it!
                values.clear();
                values.put(MediaStore.MediaColumns.IS_PENDING, 0);
                values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
                resolver.update(uri, values, null, null);
                if (listener != null) {
//                String path = "/mnt/sdcard/" + relativePath + "/" + mImageFileName;
//                String path = relativePath + "/" + mImageFileName;
                    String path = SDCardDirUtil.getAbsoluteImagePath(context, uri);
                    File file = new File(path);
                    DebugUtil.error(file.getAbsolutePath() + "1111111");
                    DebugUtil.error("-------" + uri.toString());
                    DebugUtil.error("-------" + SDCardDirUtil.getAbsoluteImagePath(context, uri));
                    if (file.exists()) {
                        // 更新图库
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(file));
                        context.sendBroadcast(intent);

//                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
//                    listener.onSuccess(relativePath);
                        DebugUtil.error("-----------22222---");
                    } else {
                        DebugUtil.error("-----------111---");
                    }
                    if (!TextUtils.isEmpty(path)) {
                        relativePath = getDirPath(path);
                    }
                    listener.onSuccess(relativePath);
                }
            } catch (Exception e) {
                resolver.delete(uri, null, null);
                DebugUtil.error(e.getMessage());
//            G.look("Exception:"+e.toString());
                if (listener != null) {
                    listener.onFailed();
                }
            }
        } else {
            saveFile(context, image, mImageFileName, listener);
        }
    }

    public interface OnSaveListener {
        void onSuccess(String filePath);

        void onFailed();
    }

    /**
     * android9及以下版本使用
     */
    private static void saveFile(Activity context, Bitmap bm, String fileName, final OnSaveListener listener) {
        if (bm == null) {
            return;
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                try {
                    String subForder = "";
                    if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
                        subForder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ClOUD_READER_PICTURE;
                    } else {
                        subForder = context.getExternalFilesDir(ClOUD_READER_PICTURE).getAbsolutePath();
                    }
                    File foder = new File(subForder);
                    if (!foder.exists()) {
                        if (foder.getParentFile() != null) {
                            foder.mkdirs();
                        }
                    }
                    File myCaptureFile = new File(subForder, fileName);
                    if (!myCaptureFile.exists()) {
                        myCaptureFile.createNewFile();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                    bos.flush();
                    bos.close();
                    emitter.onNext(myCaptureFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFailed();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(String path) {
                        if (!TextUtils.isEmpty(path)) {
                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(new File(path)));
                            context.sendBroadcast(intent);
                            if (listener != null) {
                                listener.onSuccess(getDirPath(path));
                            }
                        } else {
                            if (listener != null) {
                                listener.onFailed();
                            }
                        }
                    }
                });
        ;

    }

    /**
     * 通过uri获取文件的绝对路径
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }

    /**
     * 根据文件路径得到文件夹路径
     */
    private static String getDirPath(String path) {
        if (!TextUtils.isEmpty(path) && path.contains("/")) {
            int i = path.lastIndexOf("/");
            return path.substring(0, i);
        }
        return "";
    }
}
