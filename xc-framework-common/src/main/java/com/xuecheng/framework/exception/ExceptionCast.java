package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class ExceptionCast {

    public static CustomException cast(ResultCode resultCode) {
        return new CustomException(resultCode);
    }
}
