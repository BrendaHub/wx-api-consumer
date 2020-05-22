package com.github.niefy.modules.wx.service;

import com.github.niefy.modules.test.dao.TestMapper;
import com.github.niefy.modules.test.entity.TestEntity;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 程序发送模板消息demo
 */
@SpringBootTest
@Slf4j
@EnableAsync
class TemplateMsgServiceTest {
    @Autowired
    TemplateMsgService templateMsgService;

    @Autowired(required = false)
    TestMapper testMapper;

    /**
     * 发送模板消息给用户
     * 添加消息模板指引：https://kf.qq.com/faq/170209E3InyI170209nIF7RJ.html
     * 示例消息模板为：{{first.DATA}} ↵商品名称：{{keyword1.DATA}} ↵购买时间：{{keyword2.DATA}} ↵{{remark.DATA}}
     */
    @Test
    @Ignore
    void sendTemplateMsg() {
        List<WxMpTemplateData> data  = new ArrayList<>();
        data.add(new WxMpTemplateData("first","模板消息测试"));
        data.add(new WxMpTemplateData("keywords1","xxxxx"));
        data.add(new WxMpTemplateData("keywords2","xxxxx"));
        data.add(new WxMpTemplateData("remark","点击查看消息详情"));
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
            .templateId("模板ID")
            .url("跳转链接")
            .toUser("用户openid")
            .data(data)
            .build();
        templateMsgService.sendTemplateMsg(wxMpTemplateMessage);
    }


    @Test
    void testAdd () {
        TestEntity test = new TestEntity();
        test.setSummary("test Summary");
        test.setTitle("test Title 1 ");
        test.setType(0);
        test.setUpdateTime(new Date());
        int result = testMapper.insert(test);
        System.out.println(result + " Is result ");
    }

    @Test
    void testMyBatis() {
        log.info("test begin");
        testMapper.addType(3);
        log.info("test end");
    }
}
