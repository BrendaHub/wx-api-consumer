package com.github.niefy.modules.wx.service.impl;

import com.github.niefy.modules.wx.entity.TemplateMsgLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;

import com.github.niefy.modules.wx.dao.WxMsgMapper;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxMsgService;


@Service("wxMsgService")
@Slf4j
public class WxMsgServiceImpl extends ServiceImpl<WxMsgMapper, WxMsg> implements WxMsgService {
    /**
     * 未保存的队列
     */
    private static ConcurrentLinkedQueue<WxMsg> logsQueue = new ConcurrentLinkedQueue<>();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String msgTypes = (String)params.get("msgTypes");
        String startTime = (String)params.get("startTime");
        String openid = (String)params.get("openid");
        IPage<WxMsg> page = this.page(
                new Query<WxMsg>().getPage(params),
                new QueryWrapper<WxMsg>()
                        .in(StringUtils.isNotEmpty(msgTypes),"msg_type",msgTypes.split(","))
                        .eq(StringUtils.isNotEmpty(openid),"openid",openid)
                        .ge(StringUtils.isNotEmpty(startTime),"create_time",startTime)
        );

        return new PageUtils(page);
    }

    /**
     * 添加访问log到队列中，队列数据会定时批量插入到数据库
     * @param log
     */
    @Override
    public void addWxMsg(WxMsg log) {
        // 把消息先存到线程安全的队列中
        logsQueue.offer(log);
    }

    /**
     * 定时将日志插入到数据库
     *
     * CRON表达式    含义  秒 分 时 日 月 周 年
     * "0 0 12 * * ?"    每天中午十二点触发
     * "0 15 10 ? * *"    每天早上10：15触发
     * "0 15 10 * * ?"    每天早上10：15触发
     * "0 15 10 * * ? *"    每天早上10：15触发
     * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    synchronized void batchAddLog(){
        log.info(">>>>> INFO LOITER >>> 同步日志到DB，日志队列长度为， {}", logsQueue.size());
        List<WxMsg> logs = new ArrayList<>();
        while (!logsQueue.isEmpty()){
            //从线程安全的队列中获取一个元素
            logs.add(logsQueue.poll());
        }
        if(!logs.isEmpty()){
            this.saveBatch(logs);
        }
    }

}