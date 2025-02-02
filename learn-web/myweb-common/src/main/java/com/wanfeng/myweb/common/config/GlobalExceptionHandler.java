package com.wanfeng.myweb.common.config;

import com.wanfeng.myweb.common.exception.BizException;
import com.wanfeng.myweb.common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BizException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<?> handleUnexpectedServer(BizException ex) {
        logger.error("系统异常：", ex);
        return Result.fail("系统异常{" + ex.getErrorMsg() + "}");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<?> handlerAllException(Exception ex) {
        logger.error("系统异常：", ex);
        return Result.fail("系统异常{" + ex.getMessage() + "}");
    }
}