package com.xuecheng.cms_manage.service;

import com.xuecheng.cms_manage.dao.SysDictionaryRepository;
import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findDictionaryByType(String type) {
        SysDictionary sysDictionary = sysDictionaryRepository.findByDType(type);
        return sysDictionary;
    }
}
