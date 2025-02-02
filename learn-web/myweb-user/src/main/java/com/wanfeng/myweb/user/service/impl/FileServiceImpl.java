package com.wanfeng.myweb.user.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import com.wanfeng.myweb.user.config.OSSConfigurationProperties;
import com.wanfeng.myweb.user.dto.FileUrlDto;
import com.wanfeng.myweb.user.service.FileService;
import com.wanfeng.myweb.user.vo.FileDelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private OSS ossClient;
    @Autowired
    private OSSConfigurationProperties ossConfigurationProperties;

    @Override
    public String upload(MultipartFile file, String dirPath) {
        String bucketName = ossConfigurationProperties.getBucketName();
        String endPoint = ossConfigurationProperties.getEndPoint();

        //获取原生文件名
        String originalFilename = file.getOriginalFilename();
        String uploadFileName;
        if (dirPath == null) {
            uploadFileName = originalFilename;
        } else {
            uploadFileName = dirPath + originalFilename;
        }
        //在OSS上bucket下的文件名


        try {
            PutObjectResult result = ossClient.putObject(bucketName, uploadFileName, file.getInputStream());
            //拼装返回路径
            if (result != null) {
                return "https://" + bucketName + "." + endPoint + "/" + uploadFileName;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<Map<String, String>> listDir() {
        List<Map<String, String>> res = new ArrayList<>();
        createOssFileTree("", res);
        return res;
    }

    @Override
    public List<String> listFiles(String dir) {
        String bucketName = ossConfigurationProperties.getBucketName();
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(dir);

        // 递归列举fun目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        ArrayList<String> strings = new ArrayList<>();
        // 遍历所有文件。
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            strings.add(objectSummary.getKey());
        }
        return strings;
    }

    @Override
    public List<FileUrlDto> listForTable() {
        List<String> strings = listFiles("");
        List<FileUrlDto> fileUrlDtos = new ArrayList<>();
        for (String string : strings) {
            if (!string.contains("/")) {
                // 单层路径
                FileUrlDto fileUrlDto = new FileUrlDto();
                if (string.contains(".")) {
                    // 是个文件
                    fileUrlDto.setUrl("https://wanfeng-oss.oss-cn-beijing.aliyuncs.com/" + string);
                    fileUrlDto.setDir(false);
                } else {
                    // 只是一个空目录
                    fileUrlDto.setDir(true);
                }
                fileUrlDto.setName(string);
                fileUrlDtos.add(fileUrlDto);
            } else {
                String[] split = string.split("/");
                for (int i = 0; i < split.length; i++) {
                    {
                        FileUrlDto saved1 = isSaved(fileUrlDtos, split[i]);
                        if (saved1 == null) {
                            if (i >= 1) {
                                FileUrlDto saved = isSaved(fileUrlDtos, split[i - 1]);
                                FileUrlDto fileUrlDto = new FileUrlDto();
                                if (split[i].contains(".")) {
                                    fileUrlDto.setDir(false);
                                    fileUrlDto.setUrl("https://wanfeng-oss.oss-cn-beijing.aliyuncs.com/" + string);
                                } else {
                                    fileUrlDto.setDir(true);
                                }
                                fileUrlDto.setName(split[i]);
                                if (saved.getChildren() == null) {
                                    saved.setChildren(new ArrayList<>());
                                }
                                saved.getChildren().add(fileUrlDto);
                            } else {
                                // 该目录还未创建
                                FileUrlDto fileUrlDto = new FileUrlDto();
                                fileUrlDto.setDir(true);
                                fileUrlDto.setName(split[i]);
                                fileUrlDtos.add(fileUrlDto);
                            }
                        }
                    }
                }
            }
        }
        return fileUrlDtos;
    }

    @Override
    public boolean deleteFile(FileDelVo fileDelVo) {
        String bucketName = ossConfigurationProperties.getBucketName();
        ossClient.deleteObject(bucketName, fileDelVo.getUrl().replace("https://wanfeng-oss.oss-cn-beijing.aliyuncs.com/", ""));
        return true;
    }

    FileUrlDto isSaved(List<FileUrlDto> fileUrlDtos, String name) {
        for (FileUrlDto fileUrlDto : fileUrlDtos) {
            if (name.equals(fileUrlDto.getName())) {
                return fileUrlDto;
            }
            if (fileUrlDto.getChildren() == null) {
                continue;
            } else {
                FileUrlDto innerSave = isSaved(fileUrlDto.getChildren(), name);
                if (innerSave != null) {
                    return innerSave;
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    public void createOssFileTree(String commonPrefix, List<Map<String, String>> res) {
        String bucketName = ossConfigurationProperties.getBucketName();

        //跳出循环后设置为false，即不是最深层，不添加
        boolean flag = true;
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");

        // 列出主目录下的所有文件夹。
        listObjectsRequest.setPrefix(commonPrefix);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        int len = listing.getCommonPrefixes().size();
        if (len > 0) {
            for (String Prefix : listing.getCommonPrefixes()) {
                System.out.println(Prefix);
                createOssFileTree(Prefix, res);
            }
            flag = false;
        }
        if (flag) {
            Map<String, String> data = new HashMap<>();
            data.put("label", commonPrefix);
            res.add(data);
        }
    }
}