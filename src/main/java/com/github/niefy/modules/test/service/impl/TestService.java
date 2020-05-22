package com.github.niefy.modules.test.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.modules.test.entity.TestEntity;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 15:32 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/
public interface TestService extends IService<TestEntity> {
    /**
     * 查询文章详情，每次查询后增加点击次数
     *
     * @param id
     * @return
     */
    TestEntity findById(int id);

    /**
     * 添加或编辑文章,同名文章不可重复添加
     *
     * @param article
     */

    boolean save(TestEntity article);

    /**
     * 按条件分页查询
     *
     * @param title
     * @param page
     * @return
     */
    IPage<TestEntity> getTestEntity(String title, int page);
}
