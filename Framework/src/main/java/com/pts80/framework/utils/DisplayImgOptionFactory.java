package com.pts80.framework.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.pts80.framework.R;

public class DisplayImgOptionFactory {

    /**
     * 无默认图片显示
     */
    public static DisplayImageOptions getDefaultImgOption() {
        return new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    /**
     * 有默认的图片显示
     */
    public static DisplayImageOptions getDefaultImgOption_2() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_default_adimage)
                .showImageForEmptyUri(R.drawable.ic_default_adimage).showImageOnFail(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
    }

    /**
     * 圆角的图片显示
     */
    public static DisplayImageOptions getRoundRectImgOption() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.color.transparent)
                .showImageForEmptyUri(R.color.transparent).showImageOnFail(R.color.transparent).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new RoundedBitmapDisplayer(15)).build();
    }

    /**
     * 不缓存图片的options
     */
    public static DisplayImageOptions getNoCacheOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.color.transparent)
                .showImageForEmptyUri(R.color.transparent).showImageOnFail(R.color.transparent).cacheInMemory(false)
                .cacheOnDisk(false).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new SimpleBitmapDisplayer()).build();
    }

    /**
     * 只缓存图片到磁盘的options
     */
    public static DisplayImageOptions getOnlySdCacheOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.color.transparent)
                .showImageForEmptyUri(R.color.transparent).showImageOnFail(R.color.transparent).cacheInMemory(false)
                .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new SimpleBitmapDisplayer()).build();
    }

    /**
     * 只缓存图片到内存的options
     */
    public static DisplayImageOptions getOnlyMemeryCacheOptions() {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.color.transparent)
                .showImageForEmptyUri(R.color.transparent).showImageOnFail(R.color.transparent).cacheInMemory(true)
                .cacheOnDisk(false).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).displayer(new SimpleBitmapDisplayer()).build();
    }
}
