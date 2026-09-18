package org.example.studyroom.controller.notice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Notification;
import org.example.studyroom.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 通知表 控制器
 */
@RestController
@RequestMapping("/api/notice/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<Notification>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<Notification> page = notificationService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Notification> getById(@PathVariable Long id) {
        return Result.success(notificationService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody Notification entity) {
        return Result.success(notificationService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody Notification entity) {
        return Result.success(notificationService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(notificationService.removeById(id));
    }
}
