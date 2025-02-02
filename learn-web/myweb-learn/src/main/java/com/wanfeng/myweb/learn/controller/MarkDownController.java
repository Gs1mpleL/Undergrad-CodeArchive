package com.wanfeng.myweb.learn.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.learn.entity.MarkDownEntity;
import com.wanfeng.myweb.learn.service.MarkDownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/markdown")
@Tag(name = "MarkDown接口")
public class MarkDownController {
    @Autowired
    private MarkDownService markDownService;

    @Operation(summary = "获取所有md文章")
    @GetMapping("/list")
    public Result<?> list() {
        return Result.ok(markDownService.list());
    }

    @Operation(summary = "更新md文章")
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody MarkDownEntity markDownEntity) {
        if (Objects.equals(markDownEntity.getTitle(), "") || Objects.equals(markDownEntity.getTitle(), "无标题文章")) {
            UUID uuid = UUID.randomUUID();
            markDownEntity.setTitle(markDownEntity.getTitle() + uuid.toString().substring(0, 16));
        }
        markDownEntity.setTitle(markDownEntity.getTitle().trim());
        if (markDownEntity.getId() == null) {
            boolean save = markDownService.save(markDownEntity);
            if (save) {
                return Result.ok(markDownEntity);
            } else {
                return Result.fail(markDownEntity);
            }
        }
        boolean b = markDownService.updateById(markDownEntity);
        if (b) {
            return Result.ok(markDownEntity);
        } else {
            return Result.fail(markDownEntity);
        }

    }

    @Operation(summary = "删除md文章")
    @PostMapping("/del")
    public Result<?> delById(@RequestBody MarkDownEntity markDownEntity) {
        return Result.ok(markDownService.removeById(markDownEntity.getId()));
    }
}
