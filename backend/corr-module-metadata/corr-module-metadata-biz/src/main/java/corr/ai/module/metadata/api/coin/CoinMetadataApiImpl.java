package corr.ai.module.metadata.api.coin;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.api.CoinMetadataApi;
import corr.ai.module.metadata.converter.coin.CoinInfoConverter;
import corr.ai.module.metadata.converter.coin.FoctorConverter;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dto.coin.AvailableFoctorDTO;
import corr.ai.module.metadata.dto.coin.CoinMetadataDTO;
import corr.ai.module.metadata.service.coin.nonpricedata.AvailableNonPriceFactorsService;
import corr.ai.module.metadata.service.coin.pricedata.AvailableFoctorService;
import corr.ai.module.metadata.service.coin.pricedata.CoinMetadataInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class CoinMetadataApiImpl implements CoinMetadataApi {

    @Autowired
    private CoinMetadataInfoService coinMetadataInfoService;

    @Autowired
    private CoinInfoConverter coinInfoConverter;

    @Autowired
    private AvailableFoctorService availableFoctorService;

    @Autowired
    private FoctorConverter foctorConverter;

    @Autowired
    private AvailableNonPriceFactorsService nonPriceFactorsService;

    @Override
    public CommonResult<List<CoinMetadataDTO>> queryDataTableByCoinIdList(List<Long> ids) {
        log.info("收到RPC调用，param："+ids);
        List<CoinMetadataInfoDO> coinList = coinMetadataInfoService.listCoinByIds(ids);
        List<CoinMetadataDTO> resutl = coinList.stream().map(x -> coinInfoConverter.convertDTO(x)).collect(Collectors.toList());
        return CommonResult.success(resutl);
    }

    @Override
    public CommonResult<CoinMetadataDTO> queryCoinById(Long id) {
        CoinMetadataInfoDO coinMetadataInfoDO = coinMetadataInfoService.queryCoinById(id);
        CoinMetadataDTO coinMetadataDTO = coinInfoConverter.convertDTO(coinMetadataInfoDO);
        return CommonResult.success(coinMetadataDTO);
    }

    @Override
    public AvailableFoctorDTO queryFoctorById(Long id) {
//        AvailableFoctorDO foctorDO = availableFoctorService.getFoctorById(id);
//        AvailableFoctorDTO availableFoctorDTO = foctorConverter.convertDTO(foctorDO);
        NonPriceFactorsDO availableNonPriceFactors = nonPriceFactorsService.getAvailableNonPriceFactors(id);
        AvailableFoctorDTO availableFoctorDTO = foctorConverter.convertDTO(availableNonPriceFactors);
        return availableFoctorDTO;
    }
}
