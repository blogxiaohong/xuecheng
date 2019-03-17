package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CMS 页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public interface SysDicthinaryControllerApi {

    @ApiOperation(value = "数据字典查询接口")
    SysDictionary getByType(String type);
}
