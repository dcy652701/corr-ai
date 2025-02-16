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
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.module.gpt.service.chat.GptChatDetailsService;

@Tag(name = "用户 APP - 对话详情")
@RestController
@RequestMapping("/gpt/chat-details")
@Validated
public class AppGptChatDetailsController {

//    @Resource
//    private GptChatDetailsService chatDetailsService;
//
//    @PostMapping("/create")
//    @Operation(summary = "创建对话详情")
//    public CommonResult<Long> createChatDetails(@Valid @RequestBody AppGptChatDetailsSaveReqVO createReqVO) {
//        return success(chatDetailsService.createChatDetails(createReqVO));
//    }
//
//    @PutMapping("/update")
//    @Operation(summary = "更新对话详情")
//    public CommonResult<Boolean> updateChatDetails(@Valid @RequestBody AppGptChatDetailsSaveReqVO updateReqVO) {
//        chatDetailsService.updateChatDetails(updateReqVO);
//        return success(true);
//    }
//
//    @DeleteMapping("/delete")
//    @Operation(summary = "删除对话详情")
//    @Parameter(name = "id", description = "编号", required = true)
//    public CommonResult<Boolean> deleteChatDetails(@RequestParam("id") Long id) {
//        chatDetailsService.deleteChatDetails(id);
//        return success(true);
//    }
//
//    @GetMapping("/get")
//    @Operation(summary = "获得对话详情")
//    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    public CommonResult<AppGptChatDetailsRespVO> getChatDetails(@RequestParam("id") Long id) {
//        GptChatDetailsDO chatDetails = chatDetailsService.getChatDetails(id);
//        return success(BeanUtils.toBean(chatDetails, AppGptChatDetailsRespVO.class));
//    }
//
//    @GetMapping("/page")
//    @Operation(summary = "获得对话详情分页")
//    public CommonResult<PageResult<AppGptChatDetailsRespVO>> getChatDetailsPage(@Valid AppGptChatDetailsPageReqVO pageReqVO) {
//        PageResult<GptChatDetailsDO> pageResult = chatDetailsService.getChatDetailsPage(pageReqVO);
//        return success(BeanUtils.toBean(pageResult, AppGptChatDetailsRespVO.class));
//    }
//
//    @GetMapping("/export-excel")
//    @Operation(summary = "导出对话详情 Excel")
//    @ApiAccessLog(operateType = EXPORT)
//    public void exportChatDetailsExcel(@Valid AppGptChatDetailsPageReqVO pageReqVO,
//              HttpServletResponse response) throws IOException {
//        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
//        List<GptChatDetailsDO> list = chatDetailsService.getChatDetailsPage(pageReqVO).getList();
//        // 导出 Excel
//        ExcelUtils.write(response, "对话详情.xls", "数据", AppGptChatDetailsRespVO.class,
//                        BeanUtils.toBean(list, AppGptChatDetailsRespVO.class));
//    }

}