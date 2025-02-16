package corr.ai.module.member.api.vip;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 付费会员的api，用来查询vip权限
 *
 * @author dongchengye
 */
public interface VipApi {

    String PREFIX = ApiConstants.PREFIX + "/vip";

    /**
     * 获取所有vip的等级
     *
     * @return
     */
    @GetMapping(PREFIX + "/level-list")
    @Operation(summary = "获取所有vip等级")
    CommonResult<Map<Long,List<VipPrivilegeDTO>>> getAllVipAndPrivilege();

    /**
     * 校验vip权限
     *
     * @param level
     * @return
     */
    @GetMapping(PREFIX+"/valid")
    @Operation(summary = "校验vip权限")
    CommonResult<Boolean> validVip(@RequestParam("userId") Long level);
}
