package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCategoryMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void selectList() {
        CategoryNode categoryNode = categoryMapper.selectList();
        System.out.println(categoryNode);
    }
}
