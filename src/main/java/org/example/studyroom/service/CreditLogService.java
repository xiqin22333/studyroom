package org.example.studyroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.studyroom.entity.CreditLog;

/**
 * 信用分流水表 Service
 */
public interface CreditLogService extends IService<CreditLog> {

    /**
     * 变更用户信用分，并写流水
     * @param userId 用户ID
     * @param changeScore 变动分数（正=加分，负=扣分）
     * @param reason 变动原因
     * @param relatedType 关联类型（如 reservation）
     * @param relatedId 关联ID
     * @return 变动后的分数
     */
    int changeScore(Long userId, int changeScore, String reason, String relatedType, Long relatedId);
}
