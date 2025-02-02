package com.wanfeng.myweb.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.myweb.user.dto.MenuDto;
import com.wanfeng.myweb.user.entity.MenuEntity;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {
    List<MenuDto> buildMenu();

    Boolean removeParentAndChildren(int id);
}
