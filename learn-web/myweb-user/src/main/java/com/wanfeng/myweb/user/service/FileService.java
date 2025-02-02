package com.wanfeng.myweb.user.service;

import com.wanfeng.myweb.user.dto.FileUrlDto;
import com.wanfeng.myweb.user.vo.FileDelVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
    String upload(MultipartFile file, String dirPath);

    List<Map<String, String>> listDir();

    List<String> listFiles(String dir);

    List<FileUrlDto> listForTable();

    boolean deleteFile(FileDelVo fileDelVo);
}
