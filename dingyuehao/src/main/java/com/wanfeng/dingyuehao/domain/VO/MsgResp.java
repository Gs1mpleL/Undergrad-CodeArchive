package com.wanfeng.dingyuehao.domain.VO;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  返回给用户的消息
 */
@Data
public class MsgResp {

    private String Content;
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;


    @Override
    public String toString() {
        String format = "------\n[%s]->[%s]\ntype=[%s]\ncontent=[%s]\n-------";
        return String.format(format, FromUserName, ToUserName, MsgType, Content);
    }
}
