package com.wanfeng.myweb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PgNotice {
    private String msg;
    private String time;
    private String url;
}
