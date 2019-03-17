package com.xuecehng.search.test;

import com.xuecheng.search.ElasticsearchApplication;
import com.xuecheng.search.domain.Course;
import com.xuecheng.search.repository.CourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ElasticsearchApplication.class)
@RunWith(SpringRunner.class)
public class TestSearch {

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void testSearchAll() {

        Iterable<Course> all = courseRepository.findAll();

        all.forEach(course -> {
            System.out.println(course);
        });
    }
}
