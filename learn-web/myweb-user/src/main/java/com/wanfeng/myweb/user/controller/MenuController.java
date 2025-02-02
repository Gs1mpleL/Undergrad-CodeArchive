package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.user.dto.MenuDto;
import com.wanfeng.myweb.user.entity.MenuEntity;
import com.wanfeng.myweb.user.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "菜单管理接口")
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Operation(summary = "获取菜单列表")
    @GetMapping("/getMenu")
    public Result<?> getMenu() {
        return Result.ok(menuService.buildMenu());
    }

    @Operation(summary = "更新菜单")
    @PostMapping("/updateMenu")
    public Result<?> updateMenu(@RequestBody MenuDto menuDto) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtils.copyProperties(menuDto, menuEntity);
        return Result.ok(menuService.updateById(menuEntity));
    }

    @PostMapping("/addMenu")
    @Operation(summary = "增加菜单")
    public Result<?> addMenu(@RequestBody MenuDto menuDto) {
        MenuEntity menuEntity = new MenuEntity();
        BeanUtils.copyProperties(menuDto, menuEntity);
        return Result.ok(menuService.save(menuEntity));
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delMenu")
    public Result<?> delMenu(@RequestBody MenuDto menuDto) {
        return Result.ok(menuService.removeParentAndChildren(menuDto.getId()));
    }
}
