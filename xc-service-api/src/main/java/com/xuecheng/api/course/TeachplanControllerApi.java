package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程计划管理接口", description = "课程计划管理接口，提供课程计划的增、删、改、查")
public interface TeachplanControllerApi {

    @ApiOperation(value = "查询课程计划")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation(value = "添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);
}
