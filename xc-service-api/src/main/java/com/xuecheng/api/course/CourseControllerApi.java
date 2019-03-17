package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口", description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {

    @ApiOperation(value = "查询我的课程列表")
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation(value = "添加课程信息")
    AddCourseResult addCourcebase(CourseBase courseBase);

    @ApiOperation(value = "根据ID查询课程基本信息")
    CourseBase getCourseBaseById(String courseId);

    @ApiOperation(value = "修改课程基本信息")
    ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    @ApiOperation(value = "根据ID查询课程营销信息")
    CourseMarket getCoursemarketById(String coursemarketId);

    @ApiOperation(value = "修改课程营销信息")
    ResponseResult updateCoursemarket(String coursemarketId, CourseMarket courseMarket);

    @ApiOperation(value = "查询课程视图信息")
    CourseView courseview(String id);

    @ApiOperation(value = "课程预览")
    CoursePublishResult preview(String id);

    @ApiOperation(value = "发布课程")
    CoursePublishResult publish(String id);
}
