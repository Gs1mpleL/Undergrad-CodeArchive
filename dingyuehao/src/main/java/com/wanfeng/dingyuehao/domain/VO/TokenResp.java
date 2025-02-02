package com.wanfeng.dingyuehao.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResp {
    private String access_token;
    private String expires_in;

    public TokenResp(String access_token) {
        this.access_token = access_token;
    }
}
