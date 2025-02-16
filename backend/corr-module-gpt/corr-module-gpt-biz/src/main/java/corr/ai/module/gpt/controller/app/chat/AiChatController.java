package corr.ai.module.gpt.controller.app.chat;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.service.chat.GptChatDetailsService;
import corr.ai.module.gpt.service.chat.GptChatSessionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/gpt/chat")
@Tag(name = "AI对话接口")
public class AiChatController {

    @Autowired
    private GptChatSessionsService gptChatSessionService;

    @Autowired
    private GptChatDetailsService gptChatDetailsService;

    /**
     * 获取会话列表
     */
    @GetMapping("/sessions")
    @Operation(summary = "查询当前用户的所有会话列表")
    @PreAuthenticated
    public CommonResult<ChatVO<ChatSession>> getChatSessions() {
        ChatVO<ChatSession> chatListVO = gptChatSessionService.getChatSessionsByLoginUser();
        return CommonResult.success(chatListVO);
    }

    /**
     * 获取会话详情（带缓存）
     */
    @GetMapping("/details")
    @Operation(summary = "获取会话的详情内容")
    @PreAuthenticated
    public CommonResult<ChatVO<ChatDetail>> getChatDetails(@RequestParam("sessionId") Long sessionId) {
        ChatVO<ChatDetail> chatSessionDetails = gptChatSessionService.getChatDetailsWithCache(sessionId);
        return CommonResult.success(chatSessionDetails);
    }

    /**
     * 创建新会话
     */
    @PostMapping("/newSession")
    @Operation(summary = "创建新对话")
    @PreAuthenticated
    public CommonResult<ChatVO<ChatDetail>> createChat(@RequestBody @Valid QueryCreateChatParam createChatParam) {
        return CommonResult.success(gptChatSessionService.createNewChat(createChatParam));
    }

    @PostMapping("/continue")
    @Operation(summary = "继续对话", description = "基于已有会话继续输入对话内容")
    @PreAuthenticated
    public CommonResult<ChatVO<ChatDetail>> continueChat(@RequestBody @Valid AppGptChatDetailsSaveReqVO continueChatParam) {
        return CommonResult.success(gptChatDetailsService.continueChat(continueChatParam));
    }




}
