package corr.ai.module.metadata.controller.app.assetname;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.controller.app.assetname.resp.AssetNameVO;
import corr.ai.module.metadata.service.asset.CoinAssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-12
 * @Description: 列出可查询的数据有哪些
 * @Version: 1.0
 */
@RestController
@RequestMapping("/metadata/assetName")
@Slf4j
@Tag(name = "列出可查询的数据有哪些")
public class AssetMetaController {

    @Autowired
    private CoinAssetService coinAssetService;

    @GetMapping("/listType")
    @Operation(description = "列出可查询的类型")
    public CommonResult<List<AssetNameVO>> list(){
        return CommonResult.success(coinAssetService.listCols());
    }
}
