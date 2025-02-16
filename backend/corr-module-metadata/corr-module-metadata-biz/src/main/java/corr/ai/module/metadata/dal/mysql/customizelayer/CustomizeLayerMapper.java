package corr.ai.module.metadata.dal.mysql.customizelayer;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import org.apache.ibatis.annotations.Mapper;
import corr.ai.module.metadata.controller.app.customizelayer.vo.*;

/**
 * 用户自定义指标层 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface CustomizeLayerMapper extends BaseMapperX<CustomizeLayerDO> {

    default PageResult<CustomizeLayerDO> selectPage(AppCustomizeLayerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CustomizeLayerDO>()
                .eqIfPresent(CustomizeLayerDO::getParentFactorId, reqVO.getParentFactorId())
                .eqIfPresent(CustomizeLayerDO::getCoinId, reqVO.getCoinId())
                .eqIfPresent(CustomizeLayerDO::getIntervals, reqVO.getIntervals())
                .eqIfPresent(CustomizeLayerDO::getLayerConfig, reqVO.getLayerConfig())
                .betweenIfPresent(CustomizeLayerDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CustomizeLayerDO::getLayerId));
    }

}
