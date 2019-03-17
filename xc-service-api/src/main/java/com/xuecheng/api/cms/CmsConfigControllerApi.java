package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CmsConfig 接口")
public interface CmsConfigControllerApi {

    @ApiOperation(value = "获取 CmsConfig")
    CmsConfig getModel(String id);
}
