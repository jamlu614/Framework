package com.pts80.framework.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.pts80.framework.R;
import com.pts80.framework.utils.DisplayImgOptionFactory;
import com.pts80.framework.utils.MyDisplayMetrics;

import java.io.File;

public class BaseApplication extends Application {

    public static final String TAG = BaseApplication.class.getSimpleName();

    /**
     * volley取消中断请求，加优先级和设置tag标识
     */
    private RequestQueue mRequestQueue;

    private static BaseApplication mInstance;
    public File cacheDir;
    private DisplayImageOptions options;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        mInstance = this;
        mContext = getApplicationContext();
        MyDisplayMetrics.init(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * volley添加网络请求标识，用于取消请求
     * @param req
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * volley:退出当前请求，取消请求
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private void initImageLoader() {
        cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                getResources().getString(R.string.cache_name) + "/" + "imageCache");
        options = DisplayImgOptionFactory.getDefaultImgOption();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 1).memoryCacheExtraOptions(480, 480).threadPoolSize(4)
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024)).denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(cacheDir)).diskCacheSize(40 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
    }
}