package corr.ai.framework.vip.core.service;

import corr.ai.module.member.api.vip.VipPrivilegeDTO;

import java.util.List;
import java.util.Map;

/**
 * @author dongchengye
 */
public interface VipFrameworkService {
    /**
     * 获取所有vip等级
     *
     * @return
     */
    Map<Long, List<VipPrivilegeDTO>> getAllVipAndPrivilege();

    /**
     * 验证vip权限
     *
     * @param userId
     * @return vip的所有权益
     */
    List<Long> validVip(Long userId);

    /**
     * 根据vipId拿到所有的权益
     * @param vipId
     * @return
     */
    Map<Long,Integer> getPrivileges(Long vipId);

}
