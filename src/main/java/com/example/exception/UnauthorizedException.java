package com.example.exception;


/**
 * 描述：不能操作
 *
 * @author huchenqiang
 * @date 2018/8/27 16:58
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable t) {
        super(msg, t);
    }
}