package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.Category;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {

    CategoryNode selectList();
}
