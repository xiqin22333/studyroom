package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.studyroom.entity.CreditLog;
import org.example.studyroom.entity.SysUser;
import org.example.studyroom.mapper.CreditLogMapper;
import org.example.studyroom.service.CreditLogService;
import org.example.studyroom.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 信用分流水表 Service 实现
 */
@Service
@RequiredArgsConstructor
public class CreditLogServiceImpl extends ServiceImpl<CreditLogMapper, CreditLog> implements CreditLogService {

    private final SysUserService sysUserService;

    /** 信用分上限 */
    private static final int MAX_SCORE = 100;
    /** 信用分下限 */
    private static final int MIN_SCORE = 0;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeScore(Long userId, int changeScore, String reason, String relatedType, Long relatedId) {
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，无法变更信用分");
        }
        int before = user.getCreditScore() == null ? 100 : user.getCreditScore();
        int after = before + changeScore;
        if (after > MAX_SCORE) after = MAX_SCORE;
        if (after < MIN_SCORE) after = MIN_SCORE;
        // 更新用户信用分
        SysUser update = new SysUser();
        update.setId(userId);
        update.setCreditScore(after);
        sysUserService.updateById(update);
        // 写流水
        CreditLog log = new CreditLog();
        log.setUserId(userId);
        log.setChangeScore(changeScore);
        log.setBeforeScore(before);
        log.setAfterScore(after);
        log.setReason(reason);
        log.setRelatedType(relatedType);
        log.setRelatedId(relatedId);
        save(log);
        return after;
    }
}
