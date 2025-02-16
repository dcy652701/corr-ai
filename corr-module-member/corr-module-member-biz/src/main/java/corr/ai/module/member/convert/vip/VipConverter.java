package corr.ai.module.member.convert.vip;

import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.member.controller.app.vip.req.VipOrderCreateReq;
import corr.ai.module.member.controller.app.vip.resp.VipOrderRespVO;
import corr.ai.module.member.dal.dataobject.vip.VipOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring", imports = {SecurityFrameworkUtils.class})
public interface VipConverter {

    @Mappings({
            @Mapping(target = "applyUserId", expression = "java(SecurityFrameworkUtils.getLoginUserId())")
    })
    VipOrderDO convert(VipOrderCreateReq req);

    VipOrderRespVO convert(VipOrderDO source);
}
