package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.modules.wx.service.MsgReplyService;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;


@Component
public class MenuHandler extends AbstractHandler {
    @Autowired
    MsgReplyService msgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        logger.info("菜单事件：" + wxMessage.getEventKey());
        msgReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());
        return null;
    }


}
