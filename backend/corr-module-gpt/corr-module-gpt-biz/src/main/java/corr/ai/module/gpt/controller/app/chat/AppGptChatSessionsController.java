package corr.ai.module.gpt.controller.app.chat;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.common.util.object.BeanUtils;
import static corr.ai.framework.common.pojo.CommonResult.success;

import corr.ai.framework.excel.core.util.ExcelUtils;

import corr.ai.framework.apilog.core.annotation.ApiAccessLog;
import static corr.ai.framework.apilog.core.enums.OperateTypeEnum.*;

import corr.ai.module.gpt.controller.app.chat.vo.*;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatSessionsDO;
import corr.ai.module.gpt.service.chat.GptChatSessionsService;

@Tag(name = "AI对话接口")
@RestController
@RequestMapping("/gpt/chat")
@Validated
public class AppGptChatSessionsController {

    @Resource
    private GptChatSessionsService chatSessionsService;

//    @PostMapping("/create")
//    @Operation(summary = "创建对话信息")
//    public CommonResult<Long> createChatSessions(@Valid @RequestBody AppGptChatSessionsSaveReqVO createReqVO) {
//        return success(chatSessionsService.createChatSessions(createReqVO));
//    }

    @PutMapping("/update")
    @Operation(summary = "更改对话Session信息", description = "只能更改对话的title")
    public CommonResult<Boolean> updateChatSessions(@Valid @RequestBody AppGptChatSessionsSaveReqVO updateReqVO) {
        chatSessionsService.updateChatSessions(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除对话Session")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteChatSessions(@RequestParam("id") Long id) {
        chatSessionsService.deleteChatSessions(id);
        return success(true);
    }

//    @GetMapping("/get")
//    @Operation(summary = "获得对话信息")
//    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    public CommonResult<AppGptChatSessionsRespVO> getChatSessions(@RequestParam("id") Long id) {
//        GptChatSessionsDO chatSessions = chatSessionsService.getChatSessions(id);
//        return success(BeanUtils.toBean(chatSessions, AppGptChatSessionsRespVO.class));
//    }
//
//    @GetMapping("/page")
//    @Operation(summary = "获得对话信息分页")
//    public CommonResult<PageResult<AppGptChatSessionsRespVO>> getChatSessionsPage(@Valid AppGptChatSessionsPageReqVO pageReqVO) {
//        PageResult<GptChatSessionsDO> pageResult = chatSessionsService.getChatSessionsPage(pageReqVO);
//        return success(BeanUtils.toBean(pageResult, AppGptChatSessionsRespVO.class));
//    }
//
//    @GetMapping("/export-excel")
//    @Operation(summary = "导出对话信息 Excel")
//    @ApiAccessLog(operateType = EXPORT)
//    public void exportChatSessionsExcel(@Valid AppGptChatSessionsPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<GptChatSessionsDO> list = chatSessionsService.getChatSessionsPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "对话信息.xls", "数据", AppGptChatSessionsRespVO.class,
//                        BeanUtils.toBean(list, AppGptChatSessionsRespVO.class));
//    }

}