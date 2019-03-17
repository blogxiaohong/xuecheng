package com.xuecheng.cms_manage.controller;

import com.xuecheng.cms_manage.service.CmsPageService;
import com.xuecheng.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller
public class CmsPagePreviewController extends BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    @RequestMapping(value = "/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId) throws IOException {

        String html = cmsPageService.getPageHtml(pageId);

        ServletOutputStream out = response.getOutputStream();
        response.setHeader("Content-type","text/html;charset=utf-8");
        out.write(html.getBytes("UTF-8"));

    }
}
