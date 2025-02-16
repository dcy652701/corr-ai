package corr.ai.module.gpt.dal.mysql.chat;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import org.apache.ibatis.annotations.Mapper;
import corr.ai.module.gpt.controller.app.chat.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 对话详情 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface GptChatDetailsMapper extends BaseMapperX<GptChatDetailsDO> {

    default PageResult<GptChatDetailsDO> selectPage(AppGptChatDetailsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GptChatDetailsDO>()
                .eqIfPresent(GptChatDetailsDO::getId, reqVO.getId())
                .eqIfPresent(GptChatDetailsDO::getSessionId, reqVO.getSessionId())
                .eqIfPresent(GptChatDetailsDO::getRole, reqVO.getRole())
                .eqIfPresent(GptChatDetailsDO::getContent, reqVO.getContent())
                .eqIfPresent(GptChatDetailsDO::getChatTimestamp, reqVO.getChatTimestamp())
                .betweenIfPresent(GptChatDetailsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(GptChatDetailsDO::getId));
    }
    default List<GptChatDetailsDO> selectBySessionId(long sessionId) {
        return selectList(new LambdaQueryWrapperX<GptChatDetailsDO>()
                .eqIfPresent(GptChatDetailsDO::getSessionId, sessionId)
        );
    }

}