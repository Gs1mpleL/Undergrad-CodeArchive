package com.wanfeng.dingyuehao.service;

import com.wanfeng.dingyuehao.domain.VO.TagVo;
import com.wanfeng.dingyuehao.domain.VO.TaggingVo;
import com.wanfeng.dingyuehao.domain.VO.WxClientResq;

public interface TagService {
    TagVo create(String name);

    WxClientResq batchTagging(TaggingVo taggingVo);
}
