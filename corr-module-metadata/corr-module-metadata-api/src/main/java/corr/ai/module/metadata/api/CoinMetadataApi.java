package corr.ai.module.metadata.api;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiConstants;
import corr.ai.module.metadata.dto.coin.AvailableFoctorDTO;
import corr.ai.module.metadata.dto.coin.CoinMetadataDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 查询币表元数据的rpc接口
 *
 * @author dongchengye
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 元数据服务")
public interface CoinMetadataApi {
    String PREFIX = ApiConstants.PREFIX + "/coinTable";

    @GetMapping(PREFIX + "/queryTable")
    CommonResult<List<CoinMetadataDTO>> queryDataTableByCoinIdList(@RequestParam("ids") List<Long> ids);

    @GetMapping(PREFIX + "/queryCoin")
    CommonResult<CoinMetadataDTO> queryCoinById(@RequestParam("id") Long id);

    @GetMapping(PREFIX + "/queryFoctor")
    AvailableFoctorDTO queryFoctorById(@RequestParam("id") Long id);
}
