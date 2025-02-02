package com.wanfeng.myweb.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.myweb.user.dto.MenuDto;
import com.wanfeng.myweb.user.entity.MenuEntity;
import com.wanfeng.myweb.user.mapper.MenuMapper;
import com.wanfeng.myweb.user.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
    @Override
    public List<MenuDto> buildMenu() {
        List<MenuEntity> list = this.list();
        List<MenuDto> resList = new ArrayList<>();
        HashMap<Integer, MenuDto> map = new HashMap<>();
        List<MenuDto> tmpDtoList = new ArrayList<>();
        Set<Integer> isVis = new HashSet<>();
        // 创建所有菜单对象，并且new了孩子，存入map
        for (MenuEntity menuEntity : list) {
            MenuDto menuDto = new MenuDto();
            menuDto.setChildren(new ArrayList<>());
            BeanUtils.copyProperties(menuEntity, menuDto);
            tmpDtoList.add(menuDto);
            map.put(menuDto.getId(), menuDto);
        }

        for (MenuDto menuDto : tmpDtoList) {
            if (menuDto.getParentId() == 0) {
                if (isVis.contains(menuDto.getId())) {
                    continue;
                }
                resList.add(menuDto);
                isVis.add(menuDto.getId());
            } else { // 如果是二级菜单项
                // 如果父菜单还未创建
                if (!isVis.contains(menuDto.getParentId())) {
                    MenuDto menuDto1 = map.get(menuDto.getParentId());
                    resList.add(menuDto1);
                    isVis.add(menuDto1.getId());
                }
                map.get(menuDto.getParentId()).getChildren().add(menuDto);
                isVis.add(menuDto.getId());
            }
        }
        return resList;
    }

    @Override
    public Boolean removeParentAndChildren(int id) {
        QueryWrapper<MenuEntity> objectQueryWrapper = new QueryWrapper<>();
        QueryWrapper<MenuEntity> eq = objectQueryWrapper.eq("id", id).or().eq("parent_id", id);
        return this.remove(eq);
    }
}
