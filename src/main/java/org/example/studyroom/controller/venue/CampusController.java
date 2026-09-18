package org.example.studyroom.controller.venue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Campus;
import org.example.studyroom.service.CampusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 校区表 控制器
 */
@RestController
@RequestMapping("/api/venue/campus")
@RequiredArgsConstructor
public class CampusController {

    private final CampusService campusService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<Campus>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<Campus> page = campusService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Campus> getById(@PathVariable Long id) {
        return Result.success(campusService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody Campus entity) {
        return Result.success(campusService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody Campus entity) {
        return Result.success(campusService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(campusService.removeById(id));
    }
}
