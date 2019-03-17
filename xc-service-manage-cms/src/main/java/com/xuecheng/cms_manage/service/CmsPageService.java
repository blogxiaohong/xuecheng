package com.xuecheng.cms_manage.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.cms_manage.dao.CmsConfigRepository;
import com.xuecheng.cms_manage.dao.CmsPageRepository;
import com.xuecheng.cms_manage.dao.CmsSiteRepository;
import com.xuecheng.cms_manage.dao.CmsTemplateRepository;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    /**
     * 分页查询 CmsPage
     * @param page 页码
     * @param size 每页显示条数
     * @param queryPageRequest 查询条件实体类
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        //设置条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        //设置条件对象
        CmsPage cmspage = new CmsPage();
        //设置 站点ID
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmspage.setSiteId(queryPageRequest.getSiteId());
        }
        //设置 模板ID
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmspage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //设置 页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmspage.setPageAliase(queryPageRequest.getPageAliase());
        }
        Example<CmsPage> example = Example.of(cmspage, exampleMatcher);

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;

        if (size <= 0) {
            size = 10;
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 新增页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult saveCmsPage(CmsPage cmsPage) {

        //校验 站点ID、页面名称、页面访问路径的唯一性
        CmsPage temp = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());

        if (temp != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
//            return new CmsPageResult(CommonCode.FAIL, null);
        }

        //新增页面
        cmsPage.setPageId(null);
        cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
    }

    /**
     * 根据页面ID获取页面信息
     * @param pageId
     * @return
     */
    public CmsPage findById(String pageId) {
        Optional<CmsPage> cmspage = cmsPageRepository.findById(pageId);
        if (cmspage.isPresent()) {
            return cmspage.get();
        }
        return null;
    }

    /**
     * 修改页面
     * @param pageId
     * @param cmsPage
     * @return
     */
    public CmsPageResult editCmsPage(String pageId, CmsPage cmsPage) {

        //根据ID获取页面信息
        CmsPage one = this.findById(pageId);
        if (one != null) {
            //设置修改的页面信息
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            CmsPage result = cmsPageRepository.save(one);
            if (result != null) {
                return new CmsPageResult(CommonCode.SUCCESS, result);
            }
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    /**
     * 删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {

        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);

        if (cmsPage.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public CmsConfig getModel(String id) {
        Optional<CmsConfig> cmsconfig = cmsConfigRepository.findById(id);
        if (cmsconfig.isPresent()) {
            return cmsconfig.get();
        }
        return null;
    }

    /**
     * 获取
     * @param pageId
     * @return
     */
    public String getPageHtml(String pageId) {
        //获取数据模型
        Map model = getModelByPageId(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        //获取模板的页面信息
        String templateContent = getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(templateContent)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //页面静态化
        String content = generateHtml(templateContent, model);
        if (StringUtils.isEmpty(content)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return content;
    }

    /**
     * 页面静态化
     * @param templateContent
     * @param model
     * @return
     */
    public String generateHtml(String templateContent, Map model) {

        //设置配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //获取模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);
        //配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try {
            Template template = configuration.getTemplate("template");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取模板的页面信息
     * @param pageId
     * @return
     */
    public String getTemplateByPageId(String pageId) {

        //查询页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        //获取模板
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(templateId);
        if (cmsTemplate.isPresent()) {
            CmsTemplate cmsTemplate1 = cmsTemplate.get();
            String fileId = cmsTemplate1.getTemplateFileId();

            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);

            try {
                return IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取数据模型
     * @param pageId
     * @return
     */
    public Map getModelByPageId(String pageId) {

        //查询页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        if (entity == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        Map body = entity.getBody();
        return body;
    }

    /**
     * 页面发布
     * @param pageId
     * @return
     */
    public ResponseResult post(String pageId) {
        // 执行页面静态化
        String pageHtml = this.getPageHtml(pageId);
        // 将页面静态化文件存储到 GridFs 中
        CmsPage cmsPage = this.saveHtml(pageId, pageHtml);
        // 向 MQ 发送消息
        this.sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 向 MQ 发送消息
     * @param pageId
     */
    public void sendPostPage(String pageId) {

        //查询页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        Map<String, String> map = new HashMap<>();
        map.put("pageId", pageId);

        String jsonString = JSON.toJSONString(map);

        amqpTemplate.convertAndSend("queue_cms_postpage",cmsPage.getSiteId(),jsonString);
    }

    /**
     * 将页面静态化文件存储到 GridFs 中
     * @param pageId
     * @param pageHtml
     * @return
     */
    private CmsPage saveHtml(String pageId, String pageHtml) {

        //查询页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        ObjectId objectId = null;

        try {
            //将页面内容转换为输入流
            InputStream inputStream = IOUtils.toInputStream(pageHtml, "UTF-8");
            //将页面存储到 GridFs 中
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //将文件ID 存储到 CmsPage 中
        String fileId = objectId.toString();
        cmsPage.setHtmlFileId(fileId);
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    /**
     * 保存 CmsPage ，如果页面已经存在，则更新页面；否则添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult save(CmsPage cmsPage) {

        CmsPage isNullCmsPage = cmsPageRepository.findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());
        if (isNullCmsPage != null) {
            //更新
            this.editCmsPage(isNullCmsPage.getPageId(), cmsPage);
        }else {
            //添加
            this.saveCmsPage(cmsPage);
        }
        return new CmsPageResult(CommonCode.SUCCESS,isNullCmsPage);
    }

    /**
     * 课程一键发布
     * @param cmsPage
     * @return
     */
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //添加页面
        CmsPageResult cmsPageResult = this.save(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        CmsPage saveCmsPage = cmsPageResult.getCmsPage();
        String pageId = saveCmsPage.getPageId();

        //发布页面
        ResponseResult responseResult = this.post(pageId);
        if (!responseResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //得到页面URL：站点域名 + 站点WebPath + 页面 WebPath + 页面名称
        String siteId = saveCmsPage.getSiteId();
        //根据站点ID查询站点
        CmsSite cmsSite = this.findCmsSiteById(siteId);
        //站点域名
        String siteDomain = cmsSite.getSiteDomain();
        //站点 WebPath
        String siteWebPath = cmsSite.getSiteWebPath();
        //页面 WebPath
        String pageWebPath = cmsPage.getPageWebPath();
        //页面名称
        String pageName = cmsPage.getPageName();
        String pageUrl = siteDomain + siteWebPath + pageWebPath + pageName;
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }

    private CmsSite findCmsSiteById(String id) {
        Optional<CmsSite> cmsSiteOptional = cmsSiteRepository.findById(id);
        if (!cmsSiteOptional.isPresent()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return cmsSiteOptional.get();
    }
}
