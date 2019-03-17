package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "CMS 页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    @ApiOperation(value = "分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation(value = "新增页面")
    CmsPageResult saveCmsPage(CmsPage cmsPage);

    @ApiOperation(value = "根据页面ID获取页面信息")
    CmsPage findById(String pageId);

    @ApiOperation(value = "修改页面")
    CmsPageResult editCmsPage(String pageId, CmsPage cmsPage);

    @ApiOperation(value = "删除页面")
    ResponseResult delete(String pageId);

    @ApiOperation(value = "页面发布")
    ResponseResult post(String pageId);

    @ApiOperation(value = "保存页面")
    CmsPageResult save(CmsPage cmsPage);

    @ApiOperation(value = "课程一键发布")
    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
