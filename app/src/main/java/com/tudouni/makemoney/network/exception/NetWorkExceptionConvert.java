package com.tudouni.makemoney.network.exception;
import org.apache.http.conn.ConnectTimeoutException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class NetWorkExceptionConvert {

    public static ServiceException exceptionConvert(Throwable ex) {
        if (ex instanceof ServiceException) {
            return (ServiceException) ex;
        } else if (ex instanceof ConnectException || ex instanceof SocketException) {
            return new ServiceException("连接异常，请检查网络");
        } else if (ex instanceof UnknownHostException || ex instanceof SocketTimeoutException
                || ex instanceof IOException || ex instanceof ConnectTimeoutException) {
            return new ServiceException("网络连接失败，请重试");
        } else {
            String err = ex.toString();
            if (err.contains("HTTP 504")) {
                err = "迷路中(*^__^*) ...";
            } else if (err.contains("HTTP 502")) {
                err = "迷路中(*^__^*) ..";
            } else if (err.contains("HTTP 404")) {
                err = "迷路中(*^__^*) ....";
            } else {
                err = "";
            }
            return new ServiceException(err);
        }
    }

    /*if (e instanceof Result.ServerErrorException) {
            int status = ((Result.ServerErrorException) e).result.getStatus();
            String err = ((Result.ServerErrorException) e).result.getMessage();
            if (TextUtils.isEmpty(err)) {
                err = "迷路中(*^__^*) ...";
            }
            OnFail(status, err);
        } else {
            String err = e.toString();
            if (err.contains("HTTP 504")) {
//                err = "无网络连接";
                err = "迷路中(*^__^*) ...";
            } else if (err.contains("HTTP 502")) {
//                err = "服务器错误";
                err = "迷路中(*^__^*) ...";
            } else if (err.contains("HTTP 404")) {
//                err = "服务器无响应";
                err = "迷路中(*^__^*) ...";
            }
            OnFail(5001, err);
        }*/
}

