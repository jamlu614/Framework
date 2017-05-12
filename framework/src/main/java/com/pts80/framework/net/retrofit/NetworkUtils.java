package com.pts80.framework.net.retrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.pts80.framework.utils.NullStringToEmptyAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * Author ljb
 * Created at 2016/11/21.
 * Description
 * 网络请求工具
 */

public class NetworkUtils {

    /**
     * 解析json数据
     *
     * @return
     */
    public static <T> T parseJson(String json, Class<T> clazz) {
        return new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create().fromJson(json, clazz);
    }

    /**
     * Retrofit + okhttp
     */
    private static Retrofit mRetrofit;

    public static Retrofit getInstance(Context context) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseInterfaceValue.HOST)
                    .client(genericClient())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return mRetrofit;
        } else {
            return mRetrofit;
        }
    }

    /**
     * okhttp
     *
     * @return
     */
    public static OkHttpClient genericClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        /**
         * 添加请求头信息,校正安全性
         */
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate") // 接收的编码，是否压缩
                        .addHeader("Connection", "keep-alive") // 连接状态
                        .addHeader("Accept", "*/*") // 接收的类型
                        .build();
                return chain.proceed(request);
            }

        });
        /**
         * 添加请求拦截器
         */
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (BuildConfig.DEBUG) {
                }
                if (!(request.body() instanceof MultipartBody)) {
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
        /**
         * 添加响应结果拦截器
         */
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
        return httpClientBuilder.build();
    }

    /**
     * 上传图片
     *
     * @param files
     * @return
     */
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

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    // 从目录中加载图片
    public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(file.getAbsolutePath(), options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
