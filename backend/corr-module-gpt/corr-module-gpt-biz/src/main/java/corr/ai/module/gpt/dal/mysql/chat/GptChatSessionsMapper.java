package corr.ai.module.gpt.dal.mysql.chat;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatSessionsDO;
import org.apache.ibatis.annotations.Mapper;
import corr.ai.module.gpt.controller.app.chat.vo.*;

/**
 * 对话信息 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface GptChatSessionsMapper extends BaseMapperX<GptChatSessionsDO> {

    default PageResult<GptChatSessionsDO> selectPage(AppGptChatSessionsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GptChatSessionsDO>()
                .eqIfPresent(GptChatSessionsDO::getUserId, reqVO.getUserId())
                .eqIfPresent(GptChatSessionsDO::getTitle, reqVO.getTitle())
                .eqIfPresent(GptChatSessionsDO::getModel, reqVO.getModel())
                .betweenIfPresent(GptChatSessionsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(GptChatSessionsDO::getId));
    }

}