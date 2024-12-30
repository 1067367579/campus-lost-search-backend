package com.example.campuslostsearch.handler;

import com.example.campuslostsearch.common.result.Result;
import com.example.campuslostsearch.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex 传入异常
     * @return 错误信息
     */
    @ExceptionHandler
    public Result<Object> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result<Object> exceptionHandler(SQLIntegrityConstraintViolationException ex)
    {
        String msg = ex.getMessage();
        log.error(msg);
        if(msg.contains("Duplicate entry"))
        {
            String[] split = msg.split(" ");
            String username = split[2];
            String message = username+"已存在！";
            return Result.error(message);
        }
        return Result.error(msg);
    }

}
