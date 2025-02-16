package corr.ai.module.member.dal.mysql.userwhitelist;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.member.dal.dataobject.userwhitelist.UserWhitelistDO;
import org.apache.ibatis.annotations.Mapper;
import corr.ai.module.member.controller.admin.userwhitelist.vo.*;

/**
 * 可注册的白名单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface UserWhitelistMapper extends BaseMapperX<UserWhitelistDO> {

    default PageResult<UserWhitelistDO> selectPage(UserWhitelistPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserWhitelistDO>()
                .eqIfPresent(UserWhitelistDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(UserWhitelistDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserWhitelistDO::getWid));
    }

}
