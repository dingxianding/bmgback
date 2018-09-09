package com.example.exception;


/**
 * 描述：accesskey密码错误，或者无效，或者被删除
 *
 * @author huchenqiang
 * @date 2018/9/4 16:58
 */
public class AccessKeyInvalidException extends RuntimeException {

    public AccessKeyInvalidException(String msg) {
        super(msg);
    }

    public AccessKeyInvalidException(String msg, Throwable t) {
        super(msg, t);
    }
}