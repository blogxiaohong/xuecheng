package com.xuecheng.cms_manage.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.cms_manage.service.SysDictionaryService;
import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/dictionary")
public class SysDicthinaryController implements SysDicthinaryControllerApi {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Override
    @GetMapping("get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysDictionaryService.findDictionaryByType(type);
    }
}
