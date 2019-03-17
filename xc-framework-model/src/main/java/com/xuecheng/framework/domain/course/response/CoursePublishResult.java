package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CoursePublishResult extends ResponseResult {

    /**
     * 页面预览地址
     */
    private String previewUrl;

    public CoursePublishResult(ResultCode resultCode,String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
