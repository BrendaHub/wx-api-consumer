package com.github.niefy.modules.wx.service;

import com.alibaba.fastjson.JSON;
import com.github.niefy.modules.test.entity.TestEntity;
import com.github.niefy.modules.test.service.impl.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.validation.constraints.AssertTrue;
import java.util.Date;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 15:49 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/
@SpringBootTest
@Slf4j
@EnableAsync
public class TestEntityTest {

    @Autowired
    TestService testService;

    @Test
    void testFindID() {
        TestEntity testEntity = testService.findById(3);
        System.out.println(JSON.toJSONString(testEntity));
    }

    @Test
    void testSaveTestEntity() {
        TestEntity testEntity = new TestEntity();
        testEntity.setType(2);
        testEntity.setTitle("测试标题");
        testEntity.setSummary("测试摘要");
        testEntity.setUpdateTime(new Date());
        boolean save = testService.save(testEntity);
        Assert.assertTrue(save);
    }
}
