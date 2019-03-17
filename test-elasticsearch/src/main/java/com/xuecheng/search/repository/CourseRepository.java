package com.xuecheng.search.repository;

import com.xuecheng.search.domain.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<Course,String> {
}
