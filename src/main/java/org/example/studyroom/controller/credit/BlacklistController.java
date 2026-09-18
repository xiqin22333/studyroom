package org.example.studyroom.controller.credit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Blacklist;
import org.example.studyroom.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 黑名单表 控制器
 */
@RestController
@RequestMapping("/api/credit/blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blacklistService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<Blacklist>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<Blacklist> page = blacklistService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Blacklist> getById(@PathVariable Long id) {
        return Result.success(blacklistService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody Blacklist entity) {
        return Result.success(blacklistService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody Blacklist entity) {
        return Result.success(blacklistService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(blacklistService.removeById(id));
    }
}
