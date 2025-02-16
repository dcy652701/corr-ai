package corr.ai.module.gpt.service.chat;

import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.web.core.util.WebFrameworkUtils;
import corr.ai.module.gpt.converter.ChatConverter;
import corr.ai.module.gpt.util.OpenAIClient;
import corr.ai.module.metadata.consts.CacheKey;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.util.object.BeanUtils;

import corr.ai.module.gpt.dal.mysql.chat.GptChatDetailsMapper;

import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.gpt.enums.ErrorCodeConstants.*;

/**
 * 对话详情 Service 实现类
 *
 * @author CorrAi
 */
@Service
@Validated
public class GptChatDetailsServiceImpl implements GptChatDetailsService {

    @Resource
    private GptChatDetailsMapper chatDetailsMapper;

    @Autowired
    private RedissonClient redissonClientredissonClient;

    @Autowired
    private ChatConverter chatConverter;

    @Autowired
    private OpenAIClient openAIClient;

    @Override
    public Long createChatDetails(AppGptChatDetailsSaveReqVO createReqVO) {
        // 插入
        GptChatDetailsDO chatDetails = BeanUtils.toBean(createReqVO, GptChatDetailsDO.class);
        chatDetailsMapper.insert(chatDetails);
        // 返回
        return chatDetails.getId();
    }

    @Override
    public void updateChatDetails(AppGptChatDetailsSaveReqVO updateReqVO) {
        // 校验存在
        validateChatDetailsExists(updateReqVO.getId());
        // 更新
        GptChatDetailsDO updateObj = BeanUtils.toBean(updateReqVO, GptChatDetailsDO.class);
        chatDetailsMapper.updateById(updateObj);
    }

    @Override
    public void deleteChatDetails(Long id) {
        // 校验存在
        validateChatDetailsExists(id);
        // 删除
        chatDetailsMapper.deleteById(id);
    }

    private void validateChatDetailsExists(Long id) {
        if (chatDetailsMapper.selectById(id) == null) {
            throw exception(CHAT_DETAILS_NOT_EXISTS);
        }
    }

    @Override
    public GptChatDetailsDO getChatDetails(Long id) {
        return chatDetailsMapper.selectById(id);
    }

    @Override
    public PageResult<GptChatDetailsDO> getChatDetailsPage(AppGptChatDetailsPageReqVO pageReqVO) {
        return chatDetailsMapper.selectPage(pageReqVO);
    }

    @Override
    public ChatVO<ChatDetail> continueChat(AppGptChatDetailsSaveReqVO continueChatParam) {
        // 1. 检查会话是否存在
        if (!checkChatSessionExists(continueChatParam.getSessionId())) {
            throw exception(CHAT_DETAILS_NOT_EXISTS);
        }

        // 2. 从 Redis 中读取会话历史记录
//        RMap<String, List<ChatDetail>> detailCache = redissonClient.getMap(CacheKey.USER_CHAT_DETAILS_CACHE);
        String sessionKey = "chat_details_user_" + WebFrameworkUtils.getLoginUserId() + "_session_" + continueChatParam.getSessionId();

//        List<ChatDetail> chatDetails = detailCache.get(sessionKey);
//
//        // 如果缓存为空，从数据库查询并存入缓存
//        if (chatDetails == null) {
//            chatDetails = chatDetailsMapper.selectBySessionId(continueChatParam.getSessionId()).stream().map(x -> chatConverter.convert(x)).collect(Collectors.toList());
//            detailCache.put(sessionKey, chatDetails);
//        }

        // TODO 待删除: 暂时去掉redis, 直接读db
        List<ChatDetail> chatDetails = chatDetailsMapper.selectBySessionId(continueChatParam.getSessionId()).stream().map(x -> chatConverter.convert(x)).collect(Collectors.toList());

        // 3. 添加用户输入到会话历史
        AppGptChatDetailsSaveReqVO userMessage = new AppGptChatDetailsSaveReqVO();
        userMessage.setSessionId(continueChatParam.getSessionId());
        userMessage.setRole("user");
        userMessage.setContent(continueChatParam.getContent());
        userMessage.setChatTimestamp(LocalDateTime.now());
        Long userMessageId = createChatDetails(userMessage); // 入库

        ChatDetail userChatDetail = new ChatDetail();
        userChatDetail.setId(userMessageId);
        userChatDetail.setSessionId(continueChatParam.getSessionId());
        userChatDetail.setRole("user");
        userChatDetail.setContent(continueChatParam.getContent());
        userChatDetail.setChatTimestamp(LocalDateTime.now());
        chatDetails.add(userChatDetail);

        // 4. 拼接历史对话内容, 调用 OpenAI API
        List<Map<String, String>> messages = chatDetails.stream()
                .map(detail -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("role", detail.getRole());
                    map.put("content", detail.getContent());
                    return map;
                }).collect(Collectors.toList());
        String replyContent = openAIClient.chatCompletionsContinue(messages, continueChatParam.getModel());

        // 5. 添加模型回复到会话历史
        AppGptChatDetailsSaveReqVO assistantMessage = new AppGptChatDetailsSaveReqVO();
        assistantMessage.setSessionId(continueChatParam.getSessionId());
        assistantMessage.setRole("assistant");
        assistantMessage.setContent(replyContent);
        assistantMessage.setChatTimestamp(LocalDateTime.now());
        Long assistantMessageId = createChatDetails(assistantMessage); // 入库

        ChatDetail assistantChatDetail = new ChatDetail();
        assistantChatDetail.setId(assistantMessageId);
        assistantChatDetail.setSessionId(continueChatParam.getSessionId());
        assistantChatDetail.setRole("assistant");
        assistantChatDetail.setContent(replyContent);
        assistantChatDetail.setChatTimestamp(LocalDateTime.now());
        chatDetails.add(assistantChatDetail);

//        // 6. 更新 Redis 缓存
//        detailCache.put(sessionKey, chatDetails);

        List<ChatDetail> chatDetails1 = Collections.singletonList(assistantChatDetail);

        // 7. 返回模型的回复详细信息
        return new ChatVO<>(sessionKey, chatDetails1);
    }

    @Override
    public boolean checkChatSessionExists(Long sessionId) {
        LambdaQueryWrapperX<GptChatDetailsDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.eq(GptChatDetailsDO::getSessionId, sessionId);
        return chatDetailsMapper.exists(wrapper);
    }

}