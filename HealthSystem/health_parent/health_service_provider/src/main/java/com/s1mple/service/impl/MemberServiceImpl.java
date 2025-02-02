package com.s1mple.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.s1mple.dao.MemberDao;
import com.s1mple.pojo.Member;
import com.s1mple.service.MemberService;
import com.s1mple.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wanfeng
 * @create 2022/2/24 23:53
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        return  memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        String psw = member.getPassword();
        // 如果是使用密码注册就先加密后再存储
        if(psw!=null){
            // 加密储存
            String s = MD5Utils.md5(psw);
            member.setPassword(s);
        }
        memberDao.add(member);
    }
}
