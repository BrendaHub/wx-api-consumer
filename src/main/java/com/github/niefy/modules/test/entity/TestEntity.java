package com.github.niefy.modules.test.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.niefy.common.utils.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.annotation.Required;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Fun Description //TODO
 * @Date 2020/5/22 13:43 22
 * @Author chenhj(brenda)
 * site: https://www.ant-loiter.com
 **/
@Data
@TableName("test")
@RequiredArgsConstructor
@AllArgsConstructor
public class TestEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;
    @NotNull
    private int type;
    //  不管有没有有设置属性，所有的字段都会设置到insert语句中，如果没设置值，全为null，这种在update 操作中会有风险，把有值的更新为null
    // not_null,也是默认策略，也就是忽略null的字段，不忽略""
    // not-empty  为null，为空串的忽略，就是如果设置值为null，“”，不会插入数据库
    @TableField(insertStrategy = FieldStrategy.IGNORED)//title重复则不插入
    @NotEmpty(message = "标题不得为空")
    @NotNull
    private String title;
    @NotNull
    private String summary;
    private Date createTime;
    @NotNull
    private Date updateTime;


    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
