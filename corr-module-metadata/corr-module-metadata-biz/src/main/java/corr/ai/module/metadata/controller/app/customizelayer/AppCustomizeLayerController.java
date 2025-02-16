package corr.ai.module.metadata.controller.app.customizelayer;

import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.module.metadata.converter.customizelayer.CustomizeLayerConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

import corr.ai.module.metadata.controller.app.customizelayer.vo.*;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.module.metadata.service.customizelayer.CustomizeLayerService;

@Tag(name = "用户 APP - 用户自定义指标层")
@RestController
@RequestMapping("/metadata/customize-layer")
@Validated
public class AppCustomizeLayerController {

    @Resource
    private CustomizeLayerService customizeLayerService;

    @Autowired
    private CustomizeLayerConverter customizeLayerConverter;

    @PostMapping("/create")
    @Operation(summary = "创建用户自定义指标层")
    @PreAuthenticated
    public CommonResult<Long> createCustomizeLayer(@Valid @RequestBody AppCustomizeLayerSaveReqVO createReqVO) {
        return success(customizeLayerService.createCustomizeLayer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户自定义指标层")
    @PreAuthenticated
    public CommonResult<Boolean> updateCustomizeLayer(@Valid @RequestBody AppCustomizeLayerSaveReqVO updateReqVO) {
        customizeLayerService.updateCustomizeLayer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户自定义指标层")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deleteCustomizeLayer(@RequestParam("id") Long id) {
        customizeLayerService.deleteCustomizeLayer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户自定义指标层")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppCustomizeLayerRespVO> getCustomizeLayer(@RequestParam("id") Long id) {
        CustomizeLayerDO customizeLayer = customizeLayerService.getCustomizeLayer(id);
        return success(BeanUtils.toBean(customizeLayer, AppCustomizeLayerRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户自定义指标层分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppCustomizeLayerRespVO>> getCustomizeLayerPage(@Valid AppCustomizeLayerPageReqVO pageReqVO) {
        PageResult<CustomizeLayerDO> pageResult = customizeLayerService.getCustomizeLayerPage(pageReqVO);
        PageResult<AppCustomizeLayerRespVO> result = pageResult.map(x -> customizeLayerConverter.convert(x));
        return success(result);
    }

}
