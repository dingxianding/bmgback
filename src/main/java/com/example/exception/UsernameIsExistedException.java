package com.example.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/20 16:58
 */
public class UsernameIsExistedException extends AuthenticationException {

    public UsernameIsExistedException(String msg) {
        super(msg);
    }

    public UsernameIsExistedException(String msg, Throwable t) {
        super(msg, t);
    }
}