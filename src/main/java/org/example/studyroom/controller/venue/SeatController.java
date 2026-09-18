package org.example.studyroom.controller.venue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Seat;
import org.example.studyroom.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 座位表 控制器
 */
@RestController
@RequestMapping("/api/venue/seat")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<Seat>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<Seat> page = seatService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Seat> getById(@PathVariable Long id) {
        return Result.success(seatService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody Seat entity) {
        return Result.success(seatService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody Seat entity) {
        return Result.success(seatService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(seatService.removeById(id));
    }
}
