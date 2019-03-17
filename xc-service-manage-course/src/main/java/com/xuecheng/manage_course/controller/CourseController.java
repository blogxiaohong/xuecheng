package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import com.xuecheng.manage_course.service.CoursemarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoursemarketService coursemarketService;

    @Override
    @GetMapping("coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            CourseListRequest courseListRequest) {
        return courseService.findCourseList(page, size, courseListRequest);
    }

    @Override
    @PostMapping("coursebase/add")
    public AddCourseResult addCourcebase(@RequestBody CourseBase courseBase) {
        return courseService.addCoursebase(courseBase);
    }

    @Override
    @GetMapping("coursebase/get/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseService.getCoursebaseById(courseId);
    }

    @Override
    @PutMapping("coursebase/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId, @RequestBody CourseBase courseBase) {
        return courseService.updateCoursebase(courseId,courseBase);
    }

    @Override
    @GetMapping("coursemarket/get/{coursemarketId}")
    public CourseMarket getCoursemarketById(@PathVariable("coursemarketId") String coursemarketId) {
        return coursemarketService.getCoursemarketById(coursemarketId);
    }

    @Override
    @PutMapping("coursemarket/update/{coursemarketId}")
    public ResponseResult updateCoursemarket(@PathVariable("coursemarketId") String coursemarketId, @RequestBody CourseMarket courseMarket) {

        CourseMarket courseMarket_u = coursemarketService.updateCoursemarket(coursemarketId, courseMarket);
        if (courseMarket_u != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        } else {
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    @Override
    @GetMapping("courseview/{id}")
    public CourseView courseview(@PathVariable("id") String id) {
        return courseService.getCourseView(id);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    @Override
    @PostMapping("publish/{id}")
    public CoursePublishResult publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }
}
