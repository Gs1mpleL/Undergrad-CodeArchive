package com.wanfeng.myweb.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    List<MenuDto> children;
    private int id;
    private String path;
    private String icon;
    private String url;
    private String label;
    private int parentId;
    private String name;
}
