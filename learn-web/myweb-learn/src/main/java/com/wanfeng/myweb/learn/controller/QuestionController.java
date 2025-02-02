package com.wanfeng.myweb.learn.controller;


import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.learn.entity.QuestionEntity;
import com.wanfeng.myweb.learn.service.QuestionService;
import com.wanfeng.myweb.learn.vo.QuestionVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/question")
@Tag(name = "问答接口")
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @Operation(summary = "获取所有问题列表")
    @GetMapping("/getQuestionList/{subject}")
    public Result<List<QuestionVo>> getQuestionList(@PathVariable String subject) {
        return Result.ok(questionService.getQuestionList(subject));
    }

    @Operation(summary = "根据某个科目获取三条随机题目")
    @GetMapping("/getOneQuestion/{subject}")
    public Result<List<QuestionVo>> getOneQuestion(@PathVariable String subject) {
        return Result.ok(questionService.getThreeRandQuestionBySubject(subject));
    }

    @Operation(summary = "根据关键词搜索")
    @GetMapping("/searchByKeywords/{keyword}")
    public Result<List<QuestionVo>> searchByKeywords(@PathVariable String keyword) {
        return Result.ok(questionService.searchByKeywords(keyword));
    }

    @Operation(summary = "添加问题")
    @PostMapping("/addQuestion")
    public Result<?> addQuestion(@RequestBody QuestionVo questionVo) {
        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionVo, questionEntity);
        return Result.ok(questionService.save(questionEntity));
    }

    @Operation(summary = "获取科目列表")
    @GetMapping("/getSubjectList")
    public Result<?> getSubjectList() {
        return Result.ok(questionService.getSubjectList());
    }

    @Operation(summary = "删除问题")
    @PostMapping("deleteQuestion/{id}")
    public Result<?> deleteQuestion(@PathVariable Integer id) {
        return Result.ok(questionService.removeById(id));
    }

    @Operation(summary = "更新问题")
    @PostMapping("/updateQuestion")
    public Result<?> updateQuestion(@RequestBody QuestionVo questionVo) {
        QuestionEntity questionEntity = new QuestionEntity();
        BeanUtils.copyProperties(questionVo, questionEntity);
        return Result.ok(questionService.updateById(questionEntity));
    }
}
