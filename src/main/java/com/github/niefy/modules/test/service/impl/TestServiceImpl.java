package com.github.niefy.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.exception.RRException;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.test.dao.TestMapper;
import com.github.niefy.modules.test.entity.TestEntity;
import com.github.niefy.modules.wx.dao.ArticleMapper;
import com.github.niefy.modules.wx.dto.PageSizeConstant;
import com.github.niefy.modules.wx.entity.Article;
import com.github.niefy.modules.wx.enums.ArticleTypeEnum;
import com.github.niefy.modules.wx.service.ArticleService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 15:34 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, TestEntity> implements TestService {

    @Autowired(required =  false)
    TestMapper testMapper;
    /**
     * 查询文章列表时返回的字段（过滤掉详情字段以加快速度）
     */
    private static final String LIST_FILEDS = "id,summary,update_time,title,type,create_time";

    @Override
    public TestEntity findById(int id) {
        if (id <= 0) {
            return null;
        }
        TestEntity testEntity = testMapper.selectById(id);
        if (testEntity != null) {
            testMapper.addType(id);
        }
        return testEntity;
    }

    @Override
    public boolean save(TestEntity testEntity) {
        testEntity.setUpdateTime(new Date());
        if (testEntity.getId() > 0) {
            testMapper.updateById(testEntity);
        } else {
            String title = testEntity.getTitle();
            int count = testMapper.selectCount(
//                    new QueryWrapper<Article>().eq("title", title)
////                            .eq("category", testEntity.getCategory())
////                            .eq("sub_category", article.getSubCategory())
                    new QueryWrapper<TestEntity>().eq("title", StringUtil.trimLeft(title))
            );
            if (count > 0) throw new RRException("测试的testEntity [" + title + "]已存在，不可重复添加");
            testEntity.setCreateTime(new Date());
            testMapper.insert(testEntity);
        }
        return true;
    }

    @Override
    public IPage<TestEntity> getTestEntity(String title, int page) {
        return this.page(new Page<TestEntity>(page, PageSizeConstant.PAGE_SIZE_SMALL),
                new QueryWrapper<TestEntity>().like(!StringUtils.isEmpty("title"), "title", title)
                        .select(LIST_FILEDS)
                        .orderBy(true, false, "update_time"));
    }
}
