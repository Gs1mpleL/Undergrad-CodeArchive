package com.wanfeng.dingyuehao.feign;

import com.wanfeng.dingyuehao.domain.VO.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://api.weixin.qq.com/cgi-bin",name = "token")
public interface WxClient {
    @GetMapping("/token")
    TokenResp generalToken(@RequestParam("appid") String appid, @RequestParam("secret") String secret, @RequestParam("grant_type") String grant_type);

    @PostMapping("/message/mass/sendall")
    WxClientResq sendToAll(@RequestParam("access_token") String token, MsgToAllReq msgToAllReq);

    @PostMapping("/tags/create")
    TagVo createTag(@RequestParam("access_token") String token, TagVo tag);

    @PostMapping("/tags/members/batchtagging")
    WxClientResq batchTagging(@RequestParam("access_token") String token, TaggingVo taggingVo);
}
