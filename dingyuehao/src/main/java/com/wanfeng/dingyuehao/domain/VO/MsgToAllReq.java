package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

/**
 * {
 *    "filter":{
 *       "is_to_all":false,
 *       "tag_id":2
 *    },
 *    "text":{
 *       "content":"CONTENT"
 *    },
 *     "msgtype":"text"
 * }
 */
@Data
public class MsgToAllReq {
    private SendUserFilter filter;
    private SendText text;
    private String msgtype;
}
