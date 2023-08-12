package com.bryant.yygh.common.exception;

import com.bryant.yygh.common.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: GlobalExceptionHandler
 * Author:   swust-liuchuan
 * Date:     2022/6/29 22:55
 * Description:
 * History:
 */


/**
 * 全局异常处理类
 */
//@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(YyghException.class)  // 当出现这个异常,则会执行这个方法，需要手动抛出去
    @ResponseBody
    public Result error(YyghException e) {
        return Result.build(e.getCode(), e.getMessage());
    }
}
