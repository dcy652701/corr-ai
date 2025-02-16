package corr.ai.module.member.controller.admin.userwhitelist;

import corr.ai.module.member.service.whitelist.MemberUserWhiteListService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
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

import corr.ai.module.member.controller.admin.userwhitelist.vo.*;
import corr.ai.module.member.dal.dataobject.userwhitelist.UserWhitelistDO;
import corr.ai.module.member.service.userwhitelist.UserWhitelistService;

@Tag(name = "管理后台 - 可注册的白名单")
@RestController
@RequestMapping("/member/user-whitelist")
@Validated
public class UserWhitelistController {

    @Resource
    private UserWhitelistService userWhitelistService;

    @PostMapping("/create")
    @Operation(summary = "创建可注册的白名单")
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:create')")
    public CommonResult<Long> createUserWhitelist(@Valid @RequestBody UserWhitelistSaveReqVO createReqVO) {
        return success(userWhitelistService.createUserWhitelist(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新可注册的白名单")
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:update')")
    public CommonResult<Boolean> updateUserWhitelist(@Valid @RequestBody UserWhitelistSaveReqVO updateReqVO) {
        userWhitelistService.updateUserWhitelist(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除可注册的白名单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:delete')")
    public CommonResult<Boolean> deleteUserWhitelist(@RequestParam("id") Long id) {
        userWhitelistService.deleteUserWhitelist(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得可注册的白名单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:query')")
    public CommonResult<UserWhitelistRespVO> getUserWhitelist(@RequestParam("id") Long id) {
        UserWhitelistDO userWhitelist = userWhitelistService.getUserWhitelist(id);
        return success(BeanUtils.toBean(userWhitelist, UserWhitelistRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得可注册的白名单分页")
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:query')")
    public CommonResult<PageResult<UserWhitelistRespVO>> getUserWhitelistPage(@Valid UserWhitelistPageReqVO pageReqVO) {
        PageResult<UserWhitelistDO> pageResult = userWhitelistService.getUserWhitelistPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserWhitelistRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出可注册的白名单 Excel")
    @PreAuthorize("@ss.hasPermission('member:user-whitelist:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserWhitelistExcel(@Valid UserWhitelistPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserWhitelistDO> list = userWhitelistService.getUserWhitelistPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "可注册的白名单.xls", "数据", UserWhitelistRespVO.class,
                        BeanUtils.toBean(list, UserWhitelistRespVO.class));
    }

}
