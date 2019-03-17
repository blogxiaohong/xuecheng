package com.xuecheng.manage_course.mapper;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTeachplanMapper {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Test
    public void findTeachplanList() {

        List<Teachplan> teachplans = teachplanMapper.selectAll();
        teachplans.stream().forEach(System.out::println);
    }

    @Test
    public void insertTeachplan() {
        Teachplan teachplan = new Teachplan();
        teachplan.setPname("SpringCloud实战");
        teachplan.setParentid("0");
        teachplan.setGrade("1");
        teachplan.setPtype("1");
        teachplan.setDescription("SpringCloud");
        teachplan.setCourseid("11111");
        teachplan.setStatus("1");
        teachplan.setOrderby(1);
        teachplan.setTimelength(33.0);
        teachplan.setTrylearn(null);
        teachplanMapper.insert(teachplan);
    }

    @Test
    public void selectListTest() {
        TeachplanNode teachplanNode = teachplanMapper.selectList("4028e581617f945f01617f9dabc40000");
        System.out.println(teachplanNode);
    }

    @Test
    public void selectBootNode() {
        Teachplan teachplan = new Teachplan();
        teachplan.setCourseid("4028e581617f945f01617f9dabc40000");
        teachplan.setParentid("0");
        Teachplan boot = teachplanMapper.selectOne(teachplan);
        System.out.println(boot);
    }
}
