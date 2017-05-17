package com.jamlu.framework.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.jamlu.framework.base.BaseApplication;
import com.jamlu.framework.utils.NullStringToEmptyAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ljb on 2017/5/16.
 */

public class NetworkManager {
    private static Retrofit mRetrofit;

    public static Retrofit getInstance(Context context) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://api.map.baidu.com/telematics/v3/")
                    .client(genericClient())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return mRetrofit;
        } else {
            return mRetrofit;
        }
    }

    public static OkHttpClient genericClient() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        final String date = dateFormat.format(currentTime);
        String token = BaseApplication.getSpUtils().getString("token","");
        if (token == null) {
            token = "";
        }
        final String finalToken = token;
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        File cacheFile = new File(BaseApplication.getContext().getCacheDir(), "CacheData");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //10Mb
        httpClientBuilder
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS);
        //添加请求头信息
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .build();
                return chain.proceed(request);
            }

        });
        //添加请求拦截器
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//                if (BuildConfig.DEBUG) {
//                }
                if (request.body() instanceof FormBody) {
                    StringBuilder body = new StringBuilder("{");
                    int paramNum = ((FormBody) request.body()).size();
                    for (int i = 0; i < paramNum; i++) {
                        String name = ((FormBody) request.body()).name(i);
                        String value = ((FormBody) request.body()).value(i);
                        body.append(name)
                                .append("=")
                                .append(value);
                        if (i != paramNum - 1) {
                            body.append(",");
                        }
                    }
                    body.append("}");
                    Log.e("request", String.format("发送请求: %s\n参数：%s",
                            request.url(), body.toString()));
                }
                return chain.proceed(request);
            }
        });
//        //DEBUG模式下 添加日志拦截器
//        if (BuildConfig.DEBUG) {
//        }
        //添加响应结果拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (TextUtils.isEmpty(message)) return;
                String s = message.substring(0, 1);
                // 如果收到想响应是json才打印
                if ("{".equals(s) || "[".equals(s)) {
                    Log.e("response", "收到响应: " + message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(interceptor);
        //添加网络连接拦截器，实现缓存,只有get请求有效
//        httpClientBuilder.addInterceptor(new NetworkInterceptor());
//        httpClientBuilder.addNetworkInterceptor(new NetworkInterceptor());
        return httpClientBuilder.build();
    }

    public static class NetworkInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //没有网络时，使用缓存
            if (!com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (com.blankj.utilcode.util.NetworkUtils.isConnected()) {
                //有网络时设置缓存超时时间1分
                int maxAge = 0;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                //无网络时，设置缓存超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

            return response;
        }
    }

    public static List<MultipartBody.Part> filesToMultipartBody(List<File> files) {
        return filesToMultipartBody(files, null);
    }

    public static List<MultipartBody.Part> filesToMultipartBody(List<File> files, Map<String, String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file != null) {
                String name = file.getName();
                RequestBody requestBody = null;
                if (name.endsWith(".png") || name.endsWith(".jpg")) {
//                    Bitmap bitmap = decodeSampledBitmapFromFile(file,200,200);
                    //Bitmap zipImage = ImageUtils.compressBySampleSize(bitmap, 6, true);
                    try {
//                        if (name.endsWith(".png")) {
//                            requestBody = RequestBody.create(MediaType.parse("image/*"), ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG));
//                        } else {
//                            requestBody = RequestBody.create(MediaType.parse("image/*"), ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG));
//                        }
                        if (name.endsWith(".png")) {
                            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        } else {
                            requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        }
//                        bitmap.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                }
                if (requestBody != null) {
                    builder.addFormDataPart("file", file.getName(), requestBody);
                }
            }
        }
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return builder.build().parts();
    }

    public static List<MultipartBody.Part> fileToMultipartBody(File file, Map<String, String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (file != null) {
            String name = file.getName();
            RequestBody requestBody = null;
            if (name.endsWith(".png") || name.endsWith(".jpg")) {
//                    Bitmap bitmap = decodeSampledBitmapFromFile(file,200,200);
                //Bitmap zipImage = ImageUtils.compressBySampleSize(bitmap, 6, true);
                try {
//                        if (name.endsWith(".png")) {
//                            requestBody = RequestBody.create(MediaType.parse("image/*"), ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.PNG));
//                        } else {
//                            requestBody = RequestBody.create(MediaType.parse("image/*"), ImageUtils.bitmap2Bytes(bitmap, Bitmap.CompressFormat.JPEG));
//                        }
//                        bitmap.recycle();
                    if (name.endsWith(".png")) {
                        requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    } else {
                        requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            }
            if (requestBody != null) {
                builder.addFormDataPart("file", file.getName(), requestBody);
            }
        }
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        return builder.build().parts();
    }
}
