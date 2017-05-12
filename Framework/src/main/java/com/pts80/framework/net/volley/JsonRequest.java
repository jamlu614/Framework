package com.pts80.framework.net.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pts80.framework.base.BaseApplication;
import com.pts80.framework.utils.UiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


public class JsonRequest {

    /**
     * @param tag                (class标识,用来取消网络请求)
     * @param url                (请求地址)
     * @param which              (用于标识此次请求)
     * @param params             (请求参数)
     * @param onCompleteListener (数据请求监听接口)
     * @param isShow             (是否显示加载框)
     */
    public static void doPost(String tag, String url, final int which, Map<String, String> params,
                              final OnCompleteListener onCompleteListener, final boolean isShow) {

        Log.e("Volley", "url--->" + url.toString());
        Log.e("Volley", "params-->" + (params != null ? params.toString() : "参数为空"));
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        Request<JSONObject> request = new NormalPostRequest(url, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.e("Volley", "response --> " + response.toString());
                if (onCompleteListener != null) {
                    onCompleteListener.onResponse(response, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "ErrorResponse" + error.getMessage());
                if (onCompleteListener != null) {
                    onCompleteListener.onErrorResponse(error, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, params);

        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 2, 1.0f));
        if (onCompleteListener != null) {
            onCompleteListener.onGetDataStart(isShow);
        }
        BaseApplication.getInstance().addToRequestQueue(request, tag);
    }
    /**
     * @param tag                (class标识,用来取消网络请求)
     * @param url                (请求地址)
     * @param which              (用于标识此次请求)
     * @param params             (请求参数)
     * @param onCompleteListener (数据请求监听接口)
     * @param isShow             (是否显示加载框)
     */
    public static void doPut(String tag, String url, final int which, Map<String, String> params,
                              final OnCompleteListener onCompleteListener, final boolean isShow) {

        Log.e("Volley", "url--->" + url.toString());
        Log.e("Volley", "params-->" + (params != null ? params.toString() : "参数为空"));
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        Request<JSONObject> request = new NormalPutRequest(url, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.e("Volley", "response --> " + response.toString());
                if (onCompleteListener != null) {
                    onCompleteListener.onResponse(response, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "ErrorResponse" + error.getMessage());
                if (onCompleteListener != null) {
                    onCompleteListener.onErrorResponse(error, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, params);

        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 2, 1.0f));
        if (onCompleteListener != null) {
            onCompleteListener.onGetDataStart(isShow);
        }
        BaseApplication.getInstance().addToRequestQueue(request, tag);
    }

    public static void doGet(String tag, String url, final int which, final OnCompleteListener onCompleteListener,
                             final boolean isShow) {

        Log.e("Volley", "url--->" + url.toString());
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        Request<JSONObject> request = new JsonObjectRequest(url, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Volley", "response --> " + response.toString());
                if (onCompleteListener != null) {
                    onCompleteListener.onResponse(response, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "ErrorResponse" + error.getMessage());
                if (onCompleteListener != null) {
                    onCompleteListener.onErrorResponse(error, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        if (onCompleteListener != null) {
            onCompleteListener.onGetDataStart(isShow);
        }
        BaseApplication.getInstance().addToRequestQueue(request, tag);
    }

    public static void doDelete(String tag, String url, final int which, final OnCompleteListener onCompleteListener,
                                final boolean isShow) {

        Log.e("Volley", "url--->" + url.toString());
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        Request<JSONObject> request = new JsonObjectRequest(Request.Method.DELETE, url, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Volley", "response --> " + response.toString());
                if (onCompleteListener != null) {
                    onCompleteListener.onResponse(response, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "ErrorResponse" + error.getMessage());
                if (onCompleteListener != null) {
                    onCompleteListener.onErrorResponse(error, which);
                    onCompleteListener.onGetDataStop(isShow);
                }
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        if (onCompleteListener != null) {
            onCompleteListener.onGetDataStart(isShow);
        }
        BaseApplication.getInstance().addToRequestQueue(request, tag);
    }

    /**
     *
     * @param tag
     * @param url 上传地址
     * @param params 参数
     * @param filePartName 服务端定义上传文件的参数名字
     * @param file 要上传的文件
     * @param which  (用于标识此次请求)
     * @param onCompleteListener (数据请求监听接口)
     * @param isShow (是否显示加载框)
     */
    public static void doUploadFile(String tag, String url, Map<String, String> params, String filePartName, File file, final int which,
                                    final OnCompleteListener onCompleteListener, final boolean isShow) {
        Log.e("Volley", "url--->" + url.toString());
        Log.e("Volley", "params-->" + (params != null ? params.toString() : "参数为空"));
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        if (UiUtils.hasNetwork()) {
            Request request = new MultipartRequest(url, params, filePartName, file, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject response = new JSONObject(s);
                        Log.e("Volley", "response --> " + response.toString());
                        if (onCompleteListener!=null){
                            onCompleteListener.onResponse(response, which);
                            onCompleteListener.onGetDataStop(isShow);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", "ErrorResponse" + error.getMessage());
                    if (onCompleteListener != null) {
                        onCompleteListener.onErrorResponse(error, which);
                        onCompleteListener.onGetDataStop(isShow);
                    }
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
            if (onCompleteListener != null) {
                onCompleteListener.onGetDataStart(isShow);
            }
            HttpClientRequest.getInstance().addRequest(request);
        }
    }
    /**
     *
     * @param tag
     * @param url 上传地址
     * @param params 参数
     * @param filePartName 服务端定义上传文件的参数名字
     * @param file 要上传的文件
     * @param which  (用于标识此次请求)
     * @param onCompleteListener (数据请求监听接口)
     * @param isShow (是否显示加载框)
     */
    public static void doUploadFile2(String tag, String url, Map<String, String> params, String filePartName, List<File> file, final int which,
                                     final OnCompleteListener onCompleteListener, final boolean isShow) {
        Log.e("Volley", "url--->" + url.toString());
        Log.e("Volley", "params-->" + (params != null ? params.toString() : "参数为空"));
//        AllowX509TrustManager.allowAllSSL(); // 信任所有证书
        if (UiUtils.hasNetwork()) {
            Request request = new MultipartRequest(url, params, filePartName, file, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {
                        JSONObject response = new JSONObject(s);
                        Log.e("Volley", "response --> " + response.toString());
                        if (onCompleteListener!=null){
                            onCompleteListener.onResponse(response, which);
                            onCompleteListener.onGetDataStop(isShow);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", "ErrorResponse" + error.getMessage());
                    if (onCompleteListener != null) {
                        onCompleteListener.onErrorResponse(error, which);
                        onCompleteListener.onGetDataStop(isShow);
                    }
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
            if (onCompleteListener != null) {
                onCompleteListener.onGetDataStart(isShow);
            }
            HttpClientRequest.getInstance().addRequest(request);
        }
    }
    /**
     * 网络请求回调接口
     *
     * @author zhang
     */
    public interface OnCompleteListener {
        /**
         * 加载数据开始,判断是否启用加载提示框
         *
         * @param ishow
         */
        public void onGetDataStart(boolean ishow);

        /**
         * 加载数据结束,判断是否关闭加载提示框
         *
         * @param ishow
         */
        public void onGetDataStop(boolean ishow);

        /**
         * 网络返回响应成功
         *
         * @param response （成功返回的json）
         * @param which    (标识哪一次的请求)
         */
        public void onResponse(JSONObject response, int which);

        /**
         * 响应失败(网络异常)
         *
         * @param error （网络异常错误对象）
         * @param which （标识哪一次的请求）
         */
        public void onErrorResponse(VolleyError error, int which);
    }

    public static class NormalPostRequest extends Request<JSONObject> {
        private Map<String, String> mMap;
        private Listener<JSONObject> mListener;

        public NormalPostRequest(String url, Listener<JSONObject> listener, ErrorListener errorListener,
                                 Map<String, String> map) {
            super(Method.POST, url, errorListener);

            mListener = listener;
            mMap = map;
        }

        // mMap是已经按照前面的方式,设置了参数的实例
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mMap;
        }

        // 此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Log.e("Volley", "jsonString-->" + jsonString);
                return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(JSONObject response) {
            mListener.onResponse(response);
        }
    }
    public static class NormalPutRequest extends Request<JSONObject> {
        private Map<String, String> mMap;
        private Listener<JSONObject> mListener;

        public NormalPutRequest(String url, Listener<JSONObject> listener, ErrorListener errorListener,
                                 Map<String, String> map) {
            super(Method.PUT, url, errorListener);

            mListener = listener;
            mMap = map;
        }

        // mMap是已经按照前面的方式,设置了参数的实例
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return mMap;
        }

        // 此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Log.e("Volley", "jsonString-->" + jsonString);
                return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(JSONObject response) {
            mListener.onResponse(response);
        }
    }
}
