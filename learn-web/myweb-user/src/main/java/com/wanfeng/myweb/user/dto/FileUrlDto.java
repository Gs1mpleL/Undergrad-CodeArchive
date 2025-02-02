package com.wanfeng.myweb.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileUrlDto {
    String name;
    String url;
    boolean isDir;
    List<FileUrlDto> children;
}
