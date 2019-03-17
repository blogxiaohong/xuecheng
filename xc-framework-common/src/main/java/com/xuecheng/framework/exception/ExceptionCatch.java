package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseResult castException(CustomException e) {
        log.error("catch CustomException{}", e.getMessage());
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }
}
