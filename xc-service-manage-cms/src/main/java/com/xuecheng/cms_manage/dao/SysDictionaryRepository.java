package com.xuecheng.cms_manage.dao;


import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {

    SysDictionary findByDType(String dType);
}
