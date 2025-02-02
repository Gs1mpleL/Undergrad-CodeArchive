package com.wanfeng.dingyuehao.service.impl;

import com.wanfeng.dingyuehao.domain.prop.TokenProp;
import com.wanfeng.dingyuehao.domain.VO.TokenResp;
import com.wanfeng.dingyuehao.feign.WxClient;
import com.wanfeng.dingyuehao.domain.rediskey.TokenKey;
import com.wanfeng.dingyuehao.service.TokenService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenProp tokenProp;
    @Autowired
    private WxClient wxTokenClient;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public TokenResp getToken() {
        String key = TokenKey.tokenKey;
        String tokenFromRedis = redisTemplate.opsForValue().get(key);
        if (StringUtil.isNullOrEmpty(tokenFromRedis)){
            TokenResp tokenResp = wxTokenClient.generalToken(tokenProp.getAppid(), tokenProp.getSecret(), tokenProp.getGrant_type());
            log.info("获取Token [{}]",tokenResp.getAccess_token());
            if (tokenResp.getAccess_token() == null){
                throw new RuntimeException("Token获取失败\nAppid="+tokenProp.getAppid()+"\nsecret=" + tokenProp.getSecret());
            }
            redisTemplate.opsForValue().set(key, tokenResp.getAccess_token(), 1, TimeUnit.HOURS);
            return tokenResp;
        }
        return new TokenResp(tokenFromRedis);
    }
}
