package com.example.exception;


/**
 * 描述：用户不能删除，如管理员
 *
 * @author huchenqiang
 * @date 2018/8/27 16:58
 */
public class UserCanNotDeleteException extends RuntimeException {

    public UserCanNotDeleteException(String msg) {
        super(msg);
    }

    public UserCanNotDeleteException(String msg, Throwable t) {
        super(msg, t);
    }
}