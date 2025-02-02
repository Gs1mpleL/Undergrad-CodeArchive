package com.wanfeng.dingyuehao.component.eventCallback;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.VO.TaggingVo;
import com.wanfeng.dingyuehao.feign.WxClient;
import com.wanfeng.dingyuehao.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("subscribe")
@Slf4j
public class SubEventHandler implements EventHandler {
    @Autowired
    private WxClient wxClient;
    @Autowired
    private TokenService tokenService;

    @Override
    public void handle(MsgReq msgReq) {
        log.info("用户订阅，绑定到ALL标签");
        String fromUserName = msgReq.getFromUserName();
        TaggingVo taggingVo = new TaggingVo();
        ArrayList<String> userList = new ArrayList<>();
        userList.add(fromUserName);
        taggingVo.setOpenid_list(userList);
        taggingVo.setTagid(100L);
        System.out.println(wxClient.batchTagging(tokenService.getToken().getAccess_token(), taggingVo));
    }
}
