package com.pts80.framework.net.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Fussen on 16/7/21.
 */
public class MultipartRequest extends Request<String> {

    private MultipartEntity mMultiPartEntity = new MultipartEntity();

    private Response.Listener<String> mListener;

    private List<File> mFileParts;

    private String mFilePartName;

    private Map<String, String> mParams;

    public MultipartRequest(String url, String filePartName, File file, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(url, null, filePartName, file, listener, errorListener);
    }

    public MultipartRequest(String url, Map<String, String> params, String filePartName, File file, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mFileParts = new ArrayList<File>();
        if (file != null) {
            mFileParts.add(file);
        }
        mFilePartName = filePartName;
        mParams = params;

        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        buildMultipartEntity();
    }

    public MultipartRequest(String url, String filePartName, List<File> files, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(url, null, filePartName, files, listener, errorListener);
    }


    public MultipartRequest(String url, Map<String, String> params, String filePartName, List<File> files, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mFilePartName = filePartName;
        mFileParts = files;
        mParams = params;

        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        buildMultipartEntity();
    }

    String contentType = "";

    private void buildMultipartEntity() {
        if (mFileParts != null && mFileParts.size() > 0) {
            for (File file : mFileParts) {
//                mMultiPartEntity.addPart(mFilePartName, new FileBody(file));
                mMultiPartEntity.addPart(mFilePartName, new FileBody(file, getMIMEType(file)));
            }
            long length = mMultiPartEntity.getContentLength();

        }
        try {
            if (mParams != null && mParams.size() > 0) {
                for (Map.Entry<String, String> entry : mParams.entrySet()) {
                    mMultiPartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName("UTF-8")));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBodyContentType() {
        return mMultiPartEntity.getContentType().getValue();
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        //在这里添加固定的头
        Map<String, String> headers = new HashMap<>();
//        headers.put("BODY-X-TYPE", "2");
//        headers.put("BODY-X-VERSION", "1.0");
//        headers.put("uploadType","avatar");
        //添加cookie
//        MyApplication.addSessionCookie(headers);
        return headers;
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mMultiPartEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (response.headers != null) {
            for (Map.Entry<String, String> entry : response.headers
                    .entrySet()) {
            }
        }

        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String s) {
        mListener.onResponse(s);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */

    private String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") {
            return type;
        }
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 用于文件类型匹配
     */
    private final String[][] MIME_MapTable = {

            //{后缀名，    MIME类型}

            {".3gp", "video/3gpp"},

            {".apk", "application/vnd.android.package-archive"},

            {".asf", "video/x-ms-asf"},

            {".avi", "video/x-msvideo"},

            {".bin", "application/octet-stream"},

            {".bmp", "image/bmp"},

            {".c", "text/plain"},

            {".class", "application/octet-stream"},

            {".conf", "text/plain"},

            {".cpp", "text/plain"},

            {".doc", "application/msword"},

            {".exe", "application/octet-stream"},

            {".gif", "image/gif"},

            {".gtar", "application/x-gtar"},

            {".gz", "application/x-gzip"},

            {".h", "text/plain"},

            {".htm", "text/html"},

            {".html", "text/html"},

            {".jar", "application/java-archive"},

            {".java", "text/plain"},

            {".jpeg", "image/jpeg"},

            {".jpg", "image/jpeg"},

            {".js", "application/x-javascript"},

            {".log", "text/plain"},

            {".m3u", "audio/x-mpegurl"},

            {".m4a", "audio/mp4a-latm"},

            {".m4b", "audio/mp4a-latm"},

            {".m4p", "audio/mp4a-latm"},

            {".m4u", "video/vnd.mpegurl"},

            {".m4v", "video/x-m4v"},

            {".mov", "video/quicktime"},

            {".mp2", "audio/x-mpeg"},

            {".mp3", "audio/x-mpeg"},

            {".mp4", "video/mp4"},

            {".mpc", "application/vnd.mpohun.certificate"},

            {".mpe", "video/mpeg"},

            {".mpeg", "video/mpeg"},

            {".mpg", "video/mpeg"},

            {".mpg4", "video/mp4"},

            {".mpga", "audio/mpeg"},

            {".msg", "application/vnd.ms-outlook"},

            {".ogg", "audio/ogg"},

            {".pdf", "application/pdf"},

            {".png", "image/png"},

            {".pps", "application/vnd.ms-powerpoint"},

            {".ppt", "application/vnd.ms-powerpoint"},

            {".prop", "text/plain"},

            {".rar", "application/x-rar-compressed"},

            {".rc", "text/plain"},

            {".rmvb", "audio/x-pn-realaudio"},

            {".rtf", "application/rtf"},

            {".sh", "text/plain"},

            {".tar", "application/x-tar"},

            {".tgz", "application/x-compressed"},

            {".txt", "text/plain"},

            {".wav", "audio/x-wav"},

            {".wma", "audio/x-ms-wma"},

            {".wmv", "audio/x-ms-wmv"},

            {".wps", "application/vnd.ms-works"},

            //{".xml",    "text/xml"},

            {".xml", "text/plain"},

            {".z", "application/x-compress"},

            {".zip", "application/zip"},

            {"", "*/*"}

    };
}
