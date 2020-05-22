package com.github.niefy.modules.wx.dao;

import com.github.niefy.modules.wx.entity.WxQrCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公众号带参二维码 *
 * @date 2020-01-02 11:11:55
 */
@Mapper
// 启动mybatis的二级缓存
@CacheNamespace(flushInterval = 300000L)//缓存五分钟过期
public interface WxQrCodeMapper extends BaseMapper<WxQrCode> {

}
