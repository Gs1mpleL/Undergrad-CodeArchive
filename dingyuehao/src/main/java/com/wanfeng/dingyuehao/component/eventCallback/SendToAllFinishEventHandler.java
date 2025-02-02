package com.wanfeng.dingyuehao.component.eventCallback;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("MASSSENDJOBFINISH")
public class SendToAllFinishEventHandler implements EventHandler{
    @Override
    public void handle(MsgReq msgReq) {
        log.info("群发完成！");
    }
}
