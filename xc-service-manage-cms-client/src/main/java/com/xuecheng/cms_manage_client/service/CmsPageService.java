package com.xuecheng.cms_manage_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms_manage_client.dao.CmsPageRepository;
import com.xuecheng.cms_manage_client.dao.CmsSiteRepository;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    public void savePageToServerPath(String pageId) {

        //获取页面信息
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);

        if (!optional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        CmsPage cmsPage = optional.get();

        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(cmsPage.getSiteId());
        if (!cmsSiteOptional.isPresent()) {
            ExceptionCast.cast(CmsCode.CMS_SITE_NOTFOUND);
        }
        CmsSite cmsSite = cmsSiteOptional.get();

        //获取页面物理路径
        String pagePhysicalPath = cmsPage.getPagePhysicalPath();
        //获取站点物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        String pagePath = sitePhysicalPath + pagePhysicalPath + cmsPage.getPageName();

        //获取页面文件ID
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream inputStream = this.getFileById(htmlFileId);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public InputStream getFileById(String fileId) {

        try {
            GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(file.getObjectId());

            GridFsResource gridFsResource = new GridFsResource(file, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
