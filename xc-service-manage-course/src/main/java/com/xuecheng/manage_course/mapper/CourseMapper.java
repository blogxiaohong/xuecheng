package com.xuecheng.manage_course.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CourseMapper extends tk.mybatis.mapper.common.Mapper<CourseInfo> {

    @Select("SELECT\n" +
            "  *\n" +
            "FROM\n" +
            "  course_base base\n" +
            "  LEFT JOIN course_pic pic\n" +
            "    ON pic.courseid = base.id")
    Page<CourseInfo> selectAllWithPic();
}
