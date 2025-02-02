package com.wanfeng.dingyuehao.domain.VO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * 订阅号用户发送的消息接收体
 */
@Data
@JacksonXmlRootElement(localName ="xml")
public class MsgReq {
    @JacksonXmlProperty(localName = "Content")
    private String content;

    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;
    @JacksonXmlProperty(localName = "Event")
    private String event;
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;

    @JacksonXmlProperty(localName = "MsgId")
    private String msgId;

    @JacksonXmlProperty(localName = "MsgDataId")
    private String msgDataID;

    @JacksonXmlProperty(localName = "Idx")
    private String idx;
    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;
}
