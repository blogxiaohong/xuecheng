package com.xuecheng.search.test;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    RestClient restClient;

    /**
     * 创建索引库
     */
    @Test
    public void testCreateIndex() throws IOException {

        //创建索引库请求对象，并设置索引库名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");
        createIndexRequest.settings(
                Settings.builder()
                        .put("number_of_shards",1)
                        .put("number_of_replicas",0)
        );

        //设置映射
        createIndexRequest.mapping("doc", "{\n" +
                "\t\"properties\": {\n" +
                "\t\t\"name\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\":\"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"description\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\":\"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\":\"ik_smart\"\n" +
                "\t\t},\n" +
                "\t\t\"studymodel\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t},\n" +
                "\t\t\"price\": {\n" +
                "\t\t\t\"type\": \"float\"\n" +
                "\t\t},\n" +
                "\t\t\"timestamp\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}", XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = restHighLevelClient.indices();
        //创建索引响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //获取响应结果
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    @Test
    public void testDeleteIndex() throws IOException {

        //创建删除索引请求对象
        DeleteIndexRequest request = new DeleteIndexRequest("xc_course");
        //删除索引
        DeleteIndexResponse deleteIndexResponse = restHighLevelClient.indices().delete(request);
        //得到删除索引响应对象
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }
}
