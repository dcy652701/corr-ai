package corr.ai.module.member.api.vip;

import corr.ai.framework.common.pojo.CommonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dongchengye
 */
@RestController
@Validated
public class VipApiImpl implements VipApi{
    @Override
    public CommonResult<Map<Long, List<VipPrivilegeDTO>>> getAllVipAndPrivilege() {
        return null;
    }

    @Override
    public CommonResult<Boolean> validVip(Long level) {
        return null;
    }
}
