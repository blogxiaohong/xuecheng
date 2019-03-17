package com.xuecheng.search.test;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

    @Autowired
    RestHighLevelClient client;

    @Autowired
    RestClient restClient;

    /**
     * 搜索全部
     */
    @Test
    public void testSearchAll() throws Exception {

        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xc_course");
        searchRequest.types("doc");

        //创建搜索源构建器
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //搜索全部
        builder.query(QueryBuilders.matchAllQuery());
        //设置源字段过滤
        builder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //设置搜索源
        searchRequest.source(builder);

        //执行搜索
        SearchResponse searchResponse = client.search(searchRequest);
        //获取响应结果
        SearchHits searchHits = searchResponse.getHits();
        //总命中数
        long totalHits = searchHits.getTotalHits();
        SearchHit[] hits = searchHits.getHits();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Arrays.stream(hits).forEach(hit -> {
            //获取文档ID
            String id = hit.getId();
            //获取文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name = (String) sourceAsMap.get("name");
            //由于源字段过滤没有添加 description，所以 descaiption 是取不到的
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Double price = (Double) sourceAsMap.get("price");

            System.out.println(name);
            System.out.println(description);
            System.out.println(studymodel);
            System.out.println(price);
        });
    }
}
