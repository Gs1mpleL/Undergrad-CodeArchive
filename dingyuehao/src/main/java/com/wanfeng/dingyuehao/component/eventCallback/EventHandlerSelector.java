package com.wanfeng.dingyuehao.component.eventCallback;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Slf4j
public class EventHandlerSelector {
    @Autowired
    Map<String, EventHandler> eventHandlerMap;

    public void select(MsgReq msgReq){
        if (eventHandlerMap.get(msgReq.getEvent()) == null) {
            log.info("事件[{}]目前暂无处理器",msgReq.getEvent());
        }else {
            eventHandlerMap.get(msgReq.getEvent()).handle(msgReq);
        }
    }
}
