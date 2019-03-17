package com.xuecheng.manage_course.ribbon;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRibbon {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRibbon() {

        String serviceName = "XC-SERVICE-MANAGE-CMS";

        for (int i = 0; i < 10; i++) {
            ResponseEntity<CmsPage> entity = restTemplate.getForEntity("http://" + serviceName + "/cms/page/get/5a754adf6abb500ad05688d9", CmsPage.class);
            CmsPage cmsPage = entity.getBody();
            System.out.println(cmsPage);
        }
    }
}