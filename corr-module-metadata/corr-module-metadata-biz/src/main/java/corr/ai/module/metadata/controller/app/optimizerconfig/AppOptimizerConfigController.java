package corr.ai.module.metadata.controller.app.optimizerconfig;

import corr.ai.module.metadata.converter.optimizer.OptimizerConverter;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerResultDO;
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

import corr.ai.module.metadata.controller.app.optimizerconfig.vo.*;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerConfigDO;
import corr.ai.module.metadata.service.optimizerconfig.OptimizerConfigService;

@Tag(name = "用户 APP - 优化器信息")
@RestController
@RequestMapping("/metadata/optimizer-config")
@Validated
public class AppOptimizerConfigController {

    @Resource
    private OptimizerConfigService optimizerConfigService;

    @Autowired
    private OptimizerConverter optimizerConverter;

    @PostMapping("/create")
    @Operation(summary = "创建优化器信息")
    public CommonResult<Long> createOptimizerConfig(@Valid @RequestBody AppOptimizerConfigSaveReqVO createReqVO) {
        return success(optimizerConfigService.createOptimizerConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新优化器信息")
    public CommonResult<Boolean> updateOptimizerConfig(@Valid @RequestBody AppOptimizerConfigSaveReqVO updateReqVO) {
        optimizerConfigService.updateOptimizerConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除优化器信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOptimizerConfig(@RequestParam("id") Long id) {
        optimizerConfigService.deleteOptimizerConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得优化器信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AppOptimizerConfigRespVO> getOptimizerConfig(@RequestParam("id") Long id) {
        OptimizerConfigDO optimizerConfig = optimizerConfigService.getOptimizerConfig(id);
        return success(BeanUtils.toBean(optimizerConfig, AppOptimizerConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得优化器信息分页")
    public CommonResult<PageResult<AppOptimizerConfigRespVO>> getOptimizerConfigPage(@Valid AppOptimizerConfigPageReqVO pageReqVO) {
        PageResult<OptimizerResultDO> pageResult = optimizerConfigService.getOptimizerConfigPage(pageReqVO);
        PageResult<AppOptimizerConfigRespVO> result = pageResult.map(x -> optimizerConverter.convert(x));
        return success(result);
    }
}
