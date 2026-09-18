package org.example.studyroom.controller.venue;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.StudyRoom;
import org.example.studyroom.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 自习室表 控制器
 */
@RestController
@RequestMapping("/api/venue/room")
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<StudyRoom>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<StudyRoom> page = studyRoomService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<StudyRoom> getById(@PathVariable Long id) {
        return Result.success(studyRoomService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody StudyRoom entity) {
        return Result.success(studyRoomService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody StudyRoom entity) {
        return Result.success(studyRoomService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(studyRoomService.removeById(id));
    }
}
