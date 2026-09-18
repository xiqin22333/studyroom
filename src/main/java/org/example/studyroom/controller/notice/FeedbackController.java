package org.example.studyroom.controller.notice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Feedback;
import org.example.studyroom.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 反馈报修表 控制器
 */
@RestController
@RequestMapping("/api/notice/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<Feedback>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<Feedback> page = feedbackService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Feedback> getById(@PathVariable Long id) {
        return Result.success(feedbackService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody Feedback entity) {
        return Result.success(feedbackService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody Feedback entity) {
        return Result.success(feedbackService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(feedbackService.removeById(id));
    }
}
