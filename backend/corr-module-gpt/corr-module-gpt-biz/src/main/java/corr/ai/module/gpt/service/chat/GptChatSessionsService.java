package corr.ai.module.gpt.service.chat;

import java.util.*;
import javax.validation.*;
import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatSessionsDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;

/**
 * 对话信息 Service 接口
 *
 * @author CorrAi
 */
public interface GptChatSessionsService {

    /**
     * 创建对话信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createChatSessions(@Valid AppGptChatSessionsSaveReqVO createReqVO);

    /**
     * 更新对话信息
     *
     * @param updateReqVO 更新信息
     */
    void updateChatSessions(@Valid AppGptChatSessionsSaveReqVO updateReqVO);

    /**
     * 删除对话信息
     *
     * @param id 编号
     */
    void deleteChatSessions(Long id);

    /**
     * 获得对话信息
     *
     * @param id 编号
     * @return 对话信息
     */
    GptChatSessionsDO getChatSessions(Long id);

    /**
     * 获得对话信息分页
     *
     * @param pageReqVO 分页查询
     * @return 对话信息分页
     */
    PageResult<GptChatSessionsDO> getChatSessionsPage(AppGptChatSessionsPageReqVO pageReqVO);

    /**
     * 获取当前登录用户的聊天会话list
     * @return ChatListVO 封装的会话列表
     */
    ChatVO<ChatSession> getChatSessionsByLoginUser();

    /**
     * 获取会话详情（带缓存）
     *
     * @param sessionId 会话编号
     * @return ChatVO 包含缓存键和聊天详情列表
     */
    ChatVO<ChatDetail> getChatDetailsWithCache(Long sessionId);

    /**
     * 直接从数据库查询会话详情
     *
     * @param sessionId 会话编号
     * @return 聊天详情列表
     */
    List<GptChatDetailsDO> getChatDetails(Long sessionId);

    /**
     * 用户新建对话
     *
     * @param param 参数, 包含用户发送的第一条信息, 使用的模型名称等等
     * @return ChatSession
     */
    ChatVO<ChatDetail> createNewChat(QueryCreateChatParam param);


}