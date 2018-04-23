package com.tudouni.makemoney.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 服务器返回数据对象实例.
 * <p/>
 */
public class Result<T> {

    @SerializedName("code")
    @Expose
    private int status;

    @SerializedName("msg")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" + "status=" + status + ", message='" + message + '\'' + ", data=" + data + '}';
    }

    public static class ServerErrorException extends RuntimeException {

        public final Result result;

        public ServerErrorException(Result result) {
            this.result = result;
        }

        @Override
        public String getMessage() {
            return result.toString();
        }
    }
}
