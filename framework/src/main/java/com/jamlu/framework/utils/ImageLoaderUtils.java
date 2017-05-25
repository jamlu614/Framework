package com.jamlu.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jamlu.framework.R;
import com.jamlu.framework.utils.helper.CustomShapeTransformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author ljb
 * Created at 2016/11/8.
 * Description
 * 图片工具类
 */

public class ImageLoaderUtils {
    private static int defaultImgRes = R.drawable.ic_launcher;

    /**
     * 显示图片
     *
     * @param imgView
     * @param url
     */
    public static void loadImage(Context context, ImageView imgView, String url) {
        Glide
                .with(context)
                .load(url)
                .skipMemoryCache(false)
                .centerCrop()
                .error(defaultImgRes)
                .crossFade()
                .into(imgView);

    }

    /**
     * 显示圆形图片
     *
     * @param imgView
     * @param url
     */
    public static void loadCircleImage(Context context, ImageView imgView, String url) {
        DrawableRequestBuilder<Integer> thumbnail = Glide.with(context)
                .load(defaultImgRes)
                .bitmapTransform(new CropCircleTransformation(context));

        Glide
                .with(context)
                .load(url)
                .skipMemoryCache(false)
                .centerCrop()
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .thumbnail(thumbnail)
                .into(imgView);
    }
    /**
     * 显示圆形图片
     *
     * @param imgView
     * @param res
     */
    public static void loadCircleImage(Context context, ImageView imgView, int res) {
        DrawableRequestBuilder<Integer> thumbnail = Glide.with(context)
                .load(defaultImgRes)
                .bitmapTransform(new CropCircleTransformation(context));

        Glide
                .with(context)
                .load(res)
                .skipMemoryCache(false)
                .centerCrop()
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .thumbnail(thumbnail)
                .into(imgView);
    }

    /**
     * 显示裁剪图片
     *
     * @param imgView
     * @param path
     */
    public static void loadCutImage(final Context context, final ImageView imgView, final String path) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                subscriber.onNext(decodeBitmapByUrl(path));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        DrawableRequestBuilder<Integer> thumbnail = Glide.with(context)
                                .load(defaultImgRes)
                                .bitmapTransform(new CustomShapeTransformation(context, path, bitmap));
                        Glide
                                .with(context)
                                .load(path)
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .skipMemoryCache(false)
                                .centerCrop()
                                .crossFade()
                                .transform(new CustomShapeTransformation(context, path, bitmap))
                                .thumbnail(thumbnail)
                                .into(imgView);
                    }
                });
    }

    /**
     * url转bitmap
     *
     * @param path
     */
    public static Bitmap decodeBitmapByUrl(String path) {
        try {
            if (path.startsWith("http")) {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;

                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                byte[] result = baos.toByteArray();
                return BitmapFactory.decodeByteArray(result, 0, result.length);
            } else {
                return BitmapFactory.decodeFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置默认图片
     * @param imgRes
     */
    public static void setDefaultImgRes(@DrawableRes int imgRes) {
        defaultImgRes = imgRes;
    }
}
