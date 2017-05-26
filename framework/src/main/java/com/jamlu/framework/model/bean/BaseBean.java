package com.jamlu.framework.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mr.zhuang on 2016/12/17.
 */
public class BaseBean {

    /**
     * timestamp : 1484363061216
     * httpCode : 200
     * msg : 请求成功
     */

    private long timestamp; // 时间戳
    @SerializedName("status")
    private int httpCode; // 返回编码
    @SerializedName("message")
    private String msg; // 返回报文

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
