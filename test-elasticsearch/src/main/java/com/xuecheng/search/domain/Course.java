package com.xuecheng.search.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@ToString
@Document(indexName = "xc_course", type = "doc", shards = 1)
public class Course {

    @Id
    @Field(type = FieldType.text,index = false)
    private String id;

    @Field(type = FieldType.text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;

    @Field(type = FieldType.text, index = false)
    private String pic;

    @Field(type = FieldType.Float)
    private Float price;

    @Field(type = FieldType.keyword)
    private String studymodel;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time)
    private Date timestamp;


}
