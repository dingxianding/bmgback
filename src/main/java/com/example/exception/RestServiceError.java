package com.example.exception;

/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/23 10:33
 */
public class RestServiceError {
    private String error;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static RestServiceError build(Type errorType, String message) {
        RestServiceError error = new RestServiceError();
        error.error = errorType.getError();
        error.message = message;
        return error;
    }

    public enum Type {
        SYSTEM_BUZY("-1", "系统繁忙，此时请开发者稍候再试"),
        REQUEST_SUCCESSED("0", "请求成功"),
        REQUEST_FAILED("1", "请求失败，用于返回通用错误"),
        REQUEST_TIMEOUT("2", "请求超时"),
        ACCESS_KEY_INVALID_ERROR("1002", "获取access_token时access_key_secret错误，或者access_key_id无效"),
        USER_NOT_EXIST_ERROR("1201", "用户名不存在"),
        USER_PASSWORD_ERROR("1202", "密码错误"),
        USERNAME_EXIST_ERROR("1299", "用户名已经存在"),
        USER_CAN_NOT_DELETE_ERROR("1298", "用户不能被删除"),
        UNAUTHORIZED_ERROR("1297", "无操作权限");
        //有些对象的名称也不能重复的


        private final String error;
        private final String message;

        Type(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
