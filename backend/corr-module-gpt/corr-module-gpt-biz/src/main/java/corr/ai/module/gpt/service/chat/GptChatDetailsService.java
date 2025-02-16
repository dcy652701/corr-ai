package corr.ai.module.gpt.service.chat;

import java.util.*;
import javax.validation.*;
import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;

/**
 * 对话详情 Service 接口
 *
 * @author CorrAi
 */
public interface GptChatDetailsService {

    /**
     * 创建对话详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChatDetails(@Valid AppGptChatDetailsSaveReqVO createReqVO);

    /**
     * 更新对话详情
     *
     * @param updateReqVO 更新信息
     */
    void updateChatDetails(@Valid AppGptChatDetailsSaveReqVO updateReqVO);

    /**
     * 删除对话详情
     *
     * @param id 编号
     */
    void deleteChatDetails(Long id);

    /**
     * 获得对话详情
     *
     * @param id 编号
     * @return 对话详情
     */
    GptChatDetailsDO getChatDetails(Long id);

    /**
     * 获得对话详情分页
     *
     * @param pageReqVO 分页查询
     * @return 对话详情分页
     */
    PageResult<GptChatDetailsDO> getChatDetailsPage(AppGptChatDetailsPageReqVO pageReqVO);

    /**
     * 继续和模型对话
     *
     * @param continueChatParam 继续对话内容
     * @return 回复的对话详情
     */
    ChatVO<ChatDetail> continueChat(@Valid AppGptChatDetailsSaveReqVO continueChatParam);

    boolean checkChatSessionExists(Long sessionId);

}