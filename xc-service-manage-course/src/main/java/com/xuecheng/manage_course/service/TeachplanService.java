package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.mapper.CourseBaseMapper;
import com.xuecheng.manage_course.mapper.TeachplanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    /**
     * 查询课程计划列表
     * @param courseId
     * @return
     */
    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.selectList(courseId);
    }

    /**
     * 添加课程计划
     * @param teachplan
     * @return
     */
    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan) {

        //校验
        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getCourseid()) ||
                StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //课程计划
        //课程ID
        String courseid = teachplan.getCourseid();
        //父节点ID
        String parentid = teachplan.getParentid();
        //获取父节点
        Teachplan parentTeachplan = this.getParentTeachplan(parentid,courseid);
        parentid = parentTeachplan.getId();

        //父节点级别
        String parentGrade = parentTeachplan.getGrade();

        //添加课程计划
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan,teachplanNew);
        teachplanNew.setParentid(parentid);
        teachplanNew.setGrade(String.valueOf(Integer.parseInt(parentGrade) + 1));
        teachplanNew.setStatus("0"); //未发布
        teachplanMapper.insert(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取父节点
     * @param parentid
     * @return
     */
    private Teachplan getParentTeachplan(String parentid,String courseid) {
        Teachplan parentTeachPlan = null;
        //如果父节点为空，则获取根节点
        if (StringUtils.isEmpty(parentid)) {
            parentTeachPlan = this.getTeachplanRoot(courseid);
        } else {
            parentTeachPlan = teachplanMapper.selectByPrimaryKey(parentid);
        }
        return parentTeachPlan;
    }

    /**
     * 获取根节点ID
     * @param courseId
     * @return
     */
    private Teachplan getTeachplanRoot(String courseId) {

        //查询课程信息
        CourseBase courseBase = courseBaseMapper.selectByPrimaryKey(courseId);
        if (courseBase == null) {
            return null;
        }
        //查询课程计划根节点
        Teachplan teachplan = teachplanMapper.selectOne(new Teachplan("0", courseId));
        //如果根节点为空，则添加一个根节点
        if (teachplan == null) {
            //新添加一个课程的根结点
            teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setParentid("0");
            teachplan.setGrade("1");//一级结点
            teachplan.setStatus("0");
            teachplan.setPname(courseBase.getName());
            teachplanMapper.insert(teachplan);
            return teachplan;
        }
        return teachplan;
    }
}
