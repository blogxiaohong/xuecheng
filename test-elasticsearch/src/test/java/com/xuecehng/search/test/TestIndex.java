package com.xuecehng.search.test;

import com.xuecheng.search.ElasticsearchApplication;
import com.xuecheng.search.domain.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ElasticsearchApplication.class)
@RunWith(SpringRunner.class)
public class TestIndex {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Course.class);
    }
}
