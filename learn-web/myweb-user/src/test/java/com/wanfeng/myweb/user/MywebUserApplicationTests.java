package com.wanfeng.myweb.user;

import com.wanfeng.myweb.api.dto.PushVO;
import com.wanfeng.myweb.user.service.PushService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MywebUserApplicationTests {
    @Autowired
    PushService pushService;

    @Test
    void contextLoads() {
        //获取相关配置
        pushService.pushMac(new PushVO("考研", "测试" + "\nhttps://yz.chsi.com.cn/apply/cjcx/cjcx.do?bkdwdm=10704&xm=%E5%88%98%E5%8D%93%E6%98%8A&zjhm=141082200102170039&ksbh=107014142406069", "NOTICE", "https://yz.chsi.com.cn/apply/cjcx/cjcx.do?bkdwdm=10704&xm=%E5%88%98%E5%8D%93%E6%98%8A&zjhm=141082200102170039&ksbh=107014142406069"));

    }

}
