package com.example.exception;

/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/20 16:58
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

