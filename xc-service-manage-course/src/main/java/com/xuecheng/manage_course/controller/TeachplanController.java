package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class TeachplanController implements TeachplanControllerApi {

    @Autowired
    private TeachplanService teachplanService;

    @Override
    @GetMapping("teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return teachplanService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping(value = "teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return teachplanService.addTeachPlan(teachplan);
    }
}
