package corr.ai.module.member.dal.mysql.tag;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import corr.ai.module.member.dal.dataobject.tag.MemberTagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员标签 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface MemberTagMapper extends BaseMapperX<MemberTagDO> {

    default PageResult<MemberTagDO> selectPage(MemberTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberTagDO>()
                .likeIfPresent(MemberTagDO::getName, reqVO.getName())
                .betweenIfPresent(MemberTagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberTagDO::getId));
    }

    default MemberTagDO selelctByName(String name) {
        return selectOne(MemberTagDO::getName, name);
    }
}
