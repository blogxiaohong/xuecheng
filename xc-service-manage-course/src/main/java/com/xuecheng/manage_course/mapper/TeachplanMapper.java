package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachplanMapper extends tk.mybatis.mapper.common.Mapper<Teachplan> {

    TeachplanNode selectList(String courseId);
}
