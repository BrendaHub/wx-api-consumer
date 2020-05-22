package com.github.niefy.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.niefy.modules.test.entity.TestEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Async;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 13:42 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/
@Mapper
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface TestMapper  extends BaseMapper<TestEntity> {
    // 实现了一个异步加1的效果；
    @Async
    void addType(int id);
}
