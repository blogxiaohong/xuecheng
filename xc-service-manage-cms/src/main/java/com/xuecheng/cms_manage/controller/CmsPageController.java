package com.xuecheng.cms_manage.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.cms_manage.service.CmsPageService;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult findList(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            QueryPageRequest queryPageRequest) {

        return cmsPageService.findList(page,size,queryPageRequest);
    }

    @Override
    @PostMapping("add")
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.saveCmsPage(cmsPage);
    }

    @Override
    @GetMapping("get/{pageId}")
    public CmsPage findById(@PathVariable("pageId") String pageId) {
        return cmsPageService.findById(pageId);
    }

    @Override
    @PutMapping("edit/{pageId}")
    public CmsPageResult editCmsPage(@PathVariable("pageId") String pageId, @RequestBody CmsPage cmsPage) {
        return cmsPageService.editCmsPage(pageId,cmsPage);
    }

    @Override
    @DeleteMapping("del/{pageId}")
    public ResponseResult delete(@PathVariable("pageId") String pageId) {
        return cmsPageService.delete(pageId);
    }

    @Override
    @PostMapping("postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return cmsPageService.post(pageId);
    }

    @Override
    @PostMapping("save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return cmsPageService.save(cmsPage);
    }

    @Override
    @PostMapping("postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return cmsPageService.postPageQuick(cmsPage);
    }

}
