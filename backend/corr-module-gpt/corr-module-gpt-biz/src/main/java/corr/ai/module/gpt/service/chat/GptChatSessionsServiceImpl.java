package corr.ai.module.gpt.service.chat;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import corr.ai.framework.web.core.util.WebFrameworkUtils;
import corr.ai.module.gpt.converter.ChatConverter;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.module.gpt.dal.mysql.chat.GptChatDetailsMapper;
import corr.ai.module.gpt.util.HttpTemplate;
import corr.ai.module.gpt.util.OpenAIClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatSessionsDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.util.object.BeanUtils;
import corr.ai.module.metadata.consts.CacheKey;
import org.redisson.api.RMap;

import corr.ai.module.gpt.dal.mysql.chat.GptChatSessionsMapper;

import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.gpt.enums.ErrorCodeConstants.*;

/**
 * 对话信息 Service 实现类
 *
 * @author CorrAi
 */
@Service
@Validated
@Slf4j
public class GptChatSessionsServiceImpl implements GptChatSessionsService {

    @Resource
    private GptChatSessionsMapper chatSessionsMapper;

    @Resource
    private GptChatDetailsMapper chatDetailMapper;

    @Autowired
    private GptChatDetailsService chatDetailService;

    @Autowired
    private ChatConverter chatConverter;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private OpenAIClient openAIClient;

    @Override
    public Long createChatSessions(AppGptChatSessionsSaveReqVO createReqVO) {
        // 插入
        GptChatSessionsDO chatSessions = BeanUtils.toBean(createReqVO, GptChatSessionsDO.class);
        chatSessionsMapper.insert(chatSessions);
        // 返回
        return chatSessions.getId();
    }

    @Override
    public void updateChatSessions(AppGptChatSessionsSaveReqVO updateReqVO) {
        // 校验存在
        validateChatSessionsExists(updateReqVO.getId());
        // 更新
        GptChatSessionsDO updateObj = BeanUtils.toBean(updateReqVO, GptChatSessionsDO.class);
        chatSessionsMapper.updateById(updateObj);
    }

    @Override
    public void deleteChatSessions(Long id) {
        // 校验存在
        validateChatSessionsExists(id);
        // 删除
        chatSessionsMapper.deleteById(id);
    }

    private void validateChatSessionsExists(Long id) {
        if (chatSessionsMapper.selectById(id) == null) {
            throw exception(CHAT_SESSIONS_NOT_EXISTS);
        }
    }

    @Override
    public GptChatSessionsDO getChatSessions(Long id) {
        return chatSessionsMapper.selectById(id);
    }

    @Override
    public PageResult<GptChatSessionsDO> getChatSessionsPage(AppGptChatSessionsPageReqVO pageReqVO) {
        return chatSessionsMapper.selectPage(pageReqVO);
    }

    @Override
    public ChatVO<ChatSession> getChatSessionsByLoginUser() {
        // 获取登录用户 ID
        Long userId = WebFrameworkUtils.getLoginUserId();
        String cacheKey = "chat_sessions_user_" + userId; // TODO 只用userId当key会有问题吗?
//
//        // Redis缓存操作
//        RMap<String, List<ChatSession>> chatSessionCache = redissonClient.getMap(CacheKey.USER_CHAT_SESSION_CACHE);
//        chatSessionCache.expire(Duration.ofHours(24));
//
//        // 尝试从 Redis 中读取缓存
//        List<ChatSession> cachedSessions = chatSessionCache.get(cacheKey);
//        if (ObjectUtil.isNotNull(cachedSessions)) {
//            log.info("用户chat列表读取Redis缓存成功, key={}", cacheKey);
//            return new ChatVO<>(cacheKey, cachedSessions);
//        }

        // 如果缓存为空，进行正常查询
        AppGptChatSessionsPageReqVO pageReqVO = new AppGptChatSessionsPageReqVO();
        pageReqVO.setUserId(userId);
        List<ChatSession> sessions = getChatSessionsPage(pageReqVO)
                .getList()
                .stream()
                .map(x -> chatConverter.convert(x))
                .collect(Collectors.toList());
//        // 将查询结果存入 Redis 缓存
//        chatSessionCache.put(cacheKey, sessions);
//        log.info("用户chat列表查询数据库, 写入Redis缓存, key={}", cacheKey);
        return new ChatVO<>(cacheKey, sessions);
    }

    @Override
    public ChatVO<ChatDetail> getChatDetailsWithCache(Long sessionId) {
        Long userId = WebFrameworkUtils.getLoginUserId();

//        // Redis 缓存
//        RMap<String, List<ChatDetail>> detailsMap = redissonClient.getMap(CacheKey.USER_CHAT_DETAILS_CACHE);
//        detailsMap.expire(Duration.ofHours(24));
        String cacheKey = "chat_details_user_" + userId + "_session_" + sessionId; // TODO 只用userId当key会有问题吗?

//        // 尝试从 Redis 缓存读取
//        List<ChatDetail> cachedDetails = detailsMap.get(cacheKey);
//        if (ObjectUtil.isNotNull(cachedDetails)) {
//            log.info("用户对话详情从Redis缓存读取成功, {}", cacheKey);
//            return new ChatVO<>(cacheKey, cachedDetails);
//        }

        // 如果缓存为空，从数据库查询
        List<ChatDetail> details = getChatDetails(sessionId).stream().map(x -> chatConverter.convert(x)).collect(Collectors.toList());

//        // 写入缓存
//        if (CollUtil.isNotEmpty(details)) {
//            detailsMap.put(cacheKey, details);
//            log.info("用户对话详情查询数据库, 写入Redis缓存, key={}", cacheKey);
//        }
        return new ChatVO<>(cacheKey, details);
    }

    @Override
    public List<GptChatDetailsDO> getChatDetails(Long sessionId) {
        return chatDetailMapper.selectBySessionId(sessionId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class) // 开启mysql事务, 如果下面detail失败了会把上面的session也回滚 (Redis)
    public ChatVO<ChatDetail> createNewChat(QueryCreateChatParam createChatParam) {
        Long userId = WebFrameworkUtils.getLoginUserId();
        // Step 1: 调用 OpenAI API 获取首条回复
        String model = createChatParam.getModel();
        String content = createChatParam.getContent();
        String reply = openAIClient.createChatCompletion(content, model);
        if (reply == null) {
            throw new ServiceException(new ErrorCode(30001,"Failed to call GPT API"));
        }
        // TODO 截取前 20 个字符作为标题
        String titleText = content.substring(0, Math.min(20, content.length()));

        // Step 2: 创建 ChatSession 并保存到数据库
        AppGptChatSessionsSaveReqVO newSession = new AppGptChatSessionsSaveReqVO();
        newSession.setUserId(userId);
        newSession.setModel(model);
        newSession.setTitle(titleText);
        Long chatSessionId = createChatSessions(newSession);

        ChatSession newUserSession = new ChatSession();
        newUserSession.setTitle(titleText);
        newUserSession.setSessionId(chatSessionId);
        newUserSession.setUserId(userId);
        newUserSession.setModel(model);
        newUserSession.setCreateTime(LocalDateTime.now());

        // Step 3: 创建第一条用户消息
        AppGptChatDetailsSaveReqVO userMessage = new AppGptChatDetailsSaveReqVO();
        userMessage.setSessionId(chatSessionId);
        userMessage.setRole("user");
        userMessage.setContent(content);
        userMessage.setChatTimestamp(LocalDateTime.now());
        Long userChatId = chatDetailService.createChatDetails(userMessage);

        // Step 4: 创建第一条模型回复消息
        AppGptChatDetailsSaveReqVO assistantMessage = new AppGptChatDetailsSaveReqVO();
        assistantMessage.setSessionId(chatSessionId);
        assistantMessage.setRole("assistant");
        assistantMessage.setContent(reply);
        assistantMessage.setChatTimestamp(LocalDateTime.now());
        Long aiChatId = chatDetailService.createChatDetails(assistantMessage);

        // 创建会话详情（用户消息和助手回复）用于缓存和返回前端
        ChatDetail userDetail = new ChatDetail();
        userDetail.setId(userChatId);
        userDetail.setSessionId(chatSessionId);
        userDetail.setRole("user");
        userDetail.setContent(content);
        userDetail.setChatTimestamp(LocalDateTime.now());

        ChatDetail assistantDetail = new ChatDetail();
        assistantDetail.setId(aiChatId);
        assistantDetail.setSessionId(chatSessionId);
        assistantDetail.setRole("assistant");
        assistantDetail.setContent(reply);
        assistantDetail.setChatTimestamp(LocalDateTime.now());

        // Step 5: 缓存处理
        String userSessionCacheKey = "chat_sessions_user_" + userId;
        String chatCacheKey = "chat_details_user_" + userId + "_session_" + chatSessionId;

//        // 获取 Redis Map 并存储数据
//        RMap<String, List<ChatDetail>> chatDetailsCache = redissonClient.getMap(CacheKey.USER_CHAT_DETAILS_CACHE);

        // 创建当前会话的 ChatDetail 缓存
        List<ChatDetail> details = Arrays.asList(userDetail, assistantDetail); // 将两条消息拼起来存入列表
//        chatDetailsCache.put(chatCacheKey, details);
//        chatDetailsCache.expire(Duration.ofHours(24));
//        log.info("用户 [{}] 的 session: {} 会话数据已缓存，key={}", userId, chatSessionId, chatCacheKey);


//        RMap<String, List<ChatSession>> userSessionCache = redissonClient.getMap(CacheKey.USER_CHAT_SESSION_CACHE);
        // 更新当前用户的 ChatSessions 缓存
//        List<ChatSession> cachedSessions = userSessionCache.get(userSessionCacheKey);
//        if (cachedSessions != null) {
//            // 如果缓存不空，查询数据库并加入缓存
//            cachedSessions.add(newUserSession); // 增加新会话
//            userSessionCache.put(userSessionCacheKey, cachedSessions); // 更新缓存
//            log.info("缓存更新: 用户[{}]的会话缓存已更新, key={}", userId, userSessionCacheKey);
//        } else {
//            // 如果缓存为空，进行正常查询
//            AppGptChatSessionsPageReqVO pageReqVO = new AppGptChatSessionsPageReqVO();
//            pageReqVO.setUserId(userId);
//            List<ChatSession> allSessions = getChatSessionsPage(pageReqVO)
//                    .getList()
//                    .stream()
//                    .map(x -> chatConverter.convert(x))
//                    .collect(Collectors.toList());
//            userSessionCache.put(userSessionCacheKey, allSessions);
//            log.info("缓存新增: 已加入用户[{}]的会话列表缓存, key={}", userId, userSessionCacheKey);
//        }
//        userSessionCache.expire(Duration.ofHours(24)); // 设置过期时间

        return new ChatVO<>(chatCacheKey, details);
    }

}
