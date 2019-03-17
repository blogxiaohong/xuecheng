package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRequest {

    //站点id
    @ApiModelProperty(value = "站点ID")
    private String siteId;
    //页面ID
    @ApiModelProperty(value = "页面ID")
    private String pageId;
    //页面名称
    @ApiModelProperty(value = "页面名称")
    private String pageName;
    //别名
    @ApiModelProperty(value = "别名")
    private String pageAliase;
    //模版id
    @ApiModelProperty(value = "模板ID")
    private String templateId;

}
