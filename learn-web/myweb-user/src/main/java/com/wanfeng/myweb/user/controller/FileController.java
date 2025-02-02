package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.user.service.FileService;
import com.wanfeng.myweb.user.vo.FileDelVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@Tag(name = "文件接口")
public class FileController {
    @Autowired
    private FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file, String path) {
        return Result.ok(fileService.upload(file, path));
    }

    @Operation(summary = "列举文件夹")
    @GetMapping("/listDir")
    public Result<?> list() {
        return Result.ok(fileService.listDir());
    }

    @Operation(summary = "列举某个文件夹下的文件")
    @GetMapping("/listFiles")
    public Result<?> list(String dir) {
        return Result.ok(fileService.listFiles(dir));
    }

    @Operation(summary = "列举文件")
    @GetMapping("/listForTable")
    public Result<?> listForTable() {
        return Result.ok(fileService.listForTable());
    }

    @Operation(summary = "删除文件")
    @PostMapping("/deleteFile")
    public Result<?> listForTable(@RequestBody FileDelVo fileDelVo) {
        return Result.ok(fileService.deleteFile(fileDelVo));
    }
}
