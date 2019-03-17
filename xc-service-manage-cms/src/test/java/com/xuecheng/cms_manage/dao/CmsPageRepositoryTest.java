package com.xuecheng.cms_manage.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void findAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        all.forEach(System.out::print);
    }

    @Test
    public void findByPage() {

        Pageable pageable = PageRequest.of(1, 10);

        Page<CmsPage> all = cmsPageRepository.findAll(pageable);

        all.forEach(System.out::println);
    }
}
