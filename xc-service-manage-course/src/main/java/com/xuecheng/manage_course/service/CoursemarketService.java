package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.manage_course.mapper.CoursemarketMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoursemarketService {

    @Autowired
    private CoursemarketMapper coursemarketMapper;

    public CourseMarket getCoursemarketById(String coursemarketId) {
        CourseMarket courseMarket = coursemarketMapper.selectByPrimaryKey(coursemarketId);
        if (courseMarket == null) {
            return null;
        }
        return courseMarket;
    }

    @Transactional
    public CourseMarket updateCoursemarket(String coursemarketId, CourseMarket courseMarket) {

        CourseMarket one = coursemarketMapper.selectByPrimaryKey(coursemarketId);
        if (one != null) {
            one.setCharge(courseMarket.getCharge());
            one.setStartTime(courseMarket.getStartTime());//课程有效期，开始时间
            one.setEndTime(courseMarket.getEndTime());//课程有效期，结束时间
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            coursemarketMapper.updateByPrimaryKeySelective(one);
        }else{
            //添加课程营销信息
            one = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, one);
            //设置课程id
            one.setId(coursemarketId);
            coursemarketMapper.insert(one);
        }
        return one;
    }
}
