package com.xuecheng.manage_course.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCourseMapper {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void findAllWithPic() {
        PageHelper.startPage(1, 10);
        Page<CourseInfo> courseInfos = courseMapper.selectAllWithPic();
        System.out.println(courseInfos.getPageNum());
    }
}
