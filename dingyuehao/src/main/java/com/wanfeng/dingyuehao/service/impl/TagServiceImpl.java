package com.wanfeng.dingyuehao.service.impl;

import com.wanfeng.dingyuehao.domain.VO.*;
import com.wanfeng.dingyuehao.feign.WxClient;
import com.wanfeng.dingyuehao.service.TagService;
import com.wanfeng.dingyuehao.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private WxClient wxClient;
    @Autowired
    private TokenService tokenService;


    @Override
    public TagVo create(String name) {
        TokenResp token = tokenService.getToken();
        TagVo tag = new TagVo();
        TagData tagData = new TagData();
        tagData.setName(name);
        tag.setTag(tagData);
        tag = wxClient.createTag(token.getAccess_token(), tag);
        if (tag.getTag() == null){
            log.error("标签名称重复");
            return null;
        }
        log.info("新增标签id[{}]->[{}]",tag.getTag().getId(), tag.getTag().getName());
        return tag;
    }

    @Override
    public WxClientResq batchTagging(TaggingVo taggingVo) {
        return wxClient.batchTagging(tokenService.getToken().getAccess_token(),taggingVo);
    }
}
