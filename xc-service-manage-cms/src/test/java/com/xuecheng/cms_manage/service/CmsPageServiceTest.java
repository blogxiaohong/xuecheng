package com.xuecheng.cms_manage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceTest {

    @Autowired
    private CmsPageService cmsPageService;

    @Test
    public void testGetPageHtml() {

        String pageHtml = cmsPageService.getPageHtml("5c7aa5dcbde24e5100ba0f40");
        System.out.println(pageHtml);

    }
}
