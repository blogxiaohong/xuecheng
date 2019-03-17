package com.xuecheng.cms_manage.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTemplateTest {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    public void testStore() throws FileNotFoundException {

        File file = new File("d:/course.ftl");
        ObjectId objectId = gridFsTemplate.store(new FileInputStream(file), "course");
        System.out.println(objectId.toString());
        //5c780a28bde24e3f28d0ed8a
    }

    @Test
    public void queryFile() throws IOException {

        //根据 ID 查询文件
        String id = "5c780a28bde24e3f28d0ed8a";
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));

        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        //获取 GridFsResoutce
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");

        System.out.println(content);
    }
}
