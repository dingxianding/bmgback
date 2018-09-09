package com.example.controller;

import com.example.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：全局异常处理
 *
 * @author huchenqiang
 * @date 2018/8/20 16:58
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

    //通用错误
    //系统错误会自动处理
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Object baseErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
//        logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
//        return "---BaseException Handler---:" + e.getMessage();

        return RestServiceError.build(RestServiceError.Type.REQUEST_FAILED, ex.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
//        return "---DefaultException Handler---:" + e.getMessage();
//    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = UsernameIsExistedException.class)
    @ResponseBody
    public RestServiceError handleUsernameIsExitedException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.USERNAME_EXIST_ERROR, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseBody
    public RestServiceError handleUsernameNotFoundException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.USER_NOT_EXIST_ERROR, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseBody
    public RestServiceError handleBadCredentialsException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.USER_PASSWORD_ERROR, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = UserCanNotDeleteException.class)
    @ResponseBody
    public RestServiceError handleUserCanNotDeleteException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.USER_CAN_NOT_DELETE_ERROR, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public RestServiceError handleUnauthorizedException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.UNAUTHORIZED_ERROR, ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = AccessKeyInvalidException.class)
    @ResponseBody
    public RestServiceError handleAccessKeyInvalidException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.ACCESS_KEY_INVALID_ERROR, ex.getMessage());
    }
}