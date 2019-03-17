package com.xuecheng.cms_manage_client.listener;

import com.alibaba.fastjson.JSON;
import com.xuecheng.cms_manage_client.dao.CmsPageRepository;
import com.xuecheng.cms_manage_client.service.CmsPageService;
import com.xuecheng.framework.domain.cms.CmsPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class ConsumerPostPage {

    @Autowired
    private CmsPageService cmsPageService;

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_cms_postpage_01",durable = "true"),
            exchange = @Exchange(value = "queue_cms_postpage",type = ExchangeTypes.TOPIC),
            key = {"5c892baabde24e308c748134"}
    ))
    public void postPage(String message) {
        Map map = JSON.parseObject(message);
        String pageId = (String)map.get("pageId");

        Optional<CmsPage> cmsPageOptional = cmsPageRepository.findById(pageId);
        if (!cmsPageOptional.isPresent()) {
            log.error("receive cms post page,cmsPage is null:{}",message);
            return ;
        }
        cmsPageService.savePageToServerPath(pageId);
    }
}
