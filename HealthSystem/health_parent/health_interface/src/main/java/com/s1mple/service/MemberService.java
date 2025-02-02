package com.s1mple.service;

import com.s1mple.pojo.Member;

/**
 * @author wanfeng
 * @create 2022/2/24 23:42
 */
public interface MemberService {
    public Member findByTelephone(String telephone);
    public void add(Member member);
}
