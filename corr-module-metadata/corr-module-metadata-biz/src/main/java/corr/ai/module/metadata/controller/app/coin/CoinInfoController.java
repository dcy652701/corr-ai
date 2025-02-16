package corr.ai.module.metadata.controller.app.coin;

import cn.hutool.core.util.ObjectUtil;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.util.object.BeanUtils;
import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.module.metadata.controller.app.coin.param.CoinInfoParam;
import corr.ai.module.metadata.controller.app.coin.param.IndicatorQueryParam;
import corr.ai.module.metadata.controller.app.coin.param.IndicatorsIndQueryParam;
import corr.ai.module.metadata.controller.app.coin.resp.*;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsRespVO;
import corr.ai.module.metadata.converter.coin.AvailableIndicatorConverter;
import corr.ai.module.metadata.converter.coin.FoctorConverter;
import corr.ai.module.metadata.converter.coin.IntervalConverter;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableFoctorDO;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;
import corr.ai.module.metadata.service.coin.nonpricedata.AvailableNonPriceFactorsService;
import corr.ai.module.metadata.service.coin.pricedata.AvailableFoctorService;
import corr.ai.module.metadata.service.coin.pricedata.AvailableIndicatorService;
import corr.ai.module.metadata.service.coin.pricedata.AvailableIntervalService;
import corr.ai.module.metadata.service.coin.pricedata.CoinMetadataInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 币信息的列表
 *
 * @author dongchengye
 */
@RestController
@RequestMapping("/metadata/coin")
@Slf4j
@Tag(name = "币数据接口")
public class CoinInfoController {

    @Autowired
    private CoinMetadataInfoService coinMetadataInfoService;

    @Autowired
    private AvailableIntervalService availableIntervalService;

    @Autowired
    private AvailableFoctorService availableFoctorService;

    @Autowired
    private AvailableIndicatorService availableIndicatorService;

    @Autowired
    private FoctorConverter foctorConverter;

    @Autowired
    private IntervalConverter intervalConverter;

    @Autowired
    private AvailableIndicatorConverter availableIndicatorConverter;

    @Autowired
    private AvailableNonPriceFactorsService nonPriceFactorsService;

    @GetMapping("/page")
    @PreAuthenticated
    public CommonResult<PageResult<CoinInfoRespVO>> pageCoinList(@Valid CoinInfoParam param) {
        PageResult<CoinMetadataInfoDO> coinList = coinMetadataInfoService.pageCoins(param);
        return CommonResult.success(BeanUtils.toBean(coinList, CoinInfoRespVO.class));
    }

    @GetMapping("/listIntervals")
    @Operation(summary = "列出数仓中可查询的时间粒度")
    @PreAuthenticated
    public CommonResult<List<AvailableIntervalVO>> listIntervals() {
        List<AvailableIntervalVO> vos = availableIntervalService.listIntervals().stream().map(x -> intervalConverter.convert(x)).collect(Collectors.toList());
        return CommonResult.success(vos);
    }


    @GetMapping("/listFoctors")
    @Operation(summary = "列出数仓中可查询的列")
    @PreAuthenticated
    public CommonResult<TotalFactorVO> listFoctors(@RequestParam("coinId") Long coinId, @RequestParam("interval") String interval) {
        List<AvailableFoctorVO> priceFactor = availableFoctorService.listFoctor(coinId, interval).stream().map(x -> foctorConverter.convert(x)).collect(Collectors.toList());
        Map<String, Map<String, List<AppAvailableNonPriceFactorsRespVO>>> nonPriceFactor = nonPriceFactorsService.listNonPriceFactor(coinId, interval).stream().map(x -> foctorConverter.convert(x)).collect(Collectors.groupingBy(
                AppAvailableNonPriceFactorsRespVO::getFoctorType,
                Collectors.groupingBy(AppAvailableNonPriceFactorsRespVO::getFoctorName)
        ));
        TotalFactorVO totalFactorVO = new TotalFactorVO(priceFactor, nonPriceFactor);
        return CommonResult.success(totalFactorVO);
    }

    @GetMapping("/listKlineInds")
    @Operation(summary = "列出k线数据可计算的指标")
    @PreAuthenticated
    public CommonResult<Map<String, List<AvailableIndicatorVO>>> listKlineInds(){
        List<AvailableIndicatorsDO> availableIndicators = availableIndicatorService.listKlineIndicators();
        //把可计算的指标id set到对应的vo里
        Map<String, List<AvailableIndicatorVO>> collect = availableIndicators.stream().map(x -> {
            AvailableIndicatorVO indicator = availableIndicatorConverter.convert(x);
            indicator.setIndFlag(false);
            return indicator;
        }).collect(Collectors.groupingBy(AvailableIndicatorVO::getIndicatorClass));
        return CommonResult.success(collect);
    }

    @GetMapping("/listInds")
    @Operation(summary = "列出数仓中可计算的指标")
    @PreAuthenticated
    public CommonResult<Map<String, List<AvailableIndicatorVO>>> listInds(@Valid IndicatorQueryParam param) {
        List<Long> indicatorIds = null;
        if (param.getIsPrice()) {
            AvailableFoctorDO foctorDO = availableFoctorService.getFoctorById(param.getFoctorId());
            indicatorIds = foctorDO.getIndicators();
        } else {
            indicatorIds = availableIndicatorService.listIndicatorIds();
        }
//        List<Long> indicatorIds = foctorDO.getIndicators();
        indicatorIds.removeIf(indId -> indId == 0L);
        if (indicatorIds.isEmpty()) {
            return CommonResult.success(null);
        }
        //列出factor下的所有指标
        List<AvailableIndicatorsDO> availableIndicators = availableIndicatorService.listIndicators(indicatorIds);
        //列出所有指标下可计算的指标id
        Map<Long, List<Long>> indAndSubIndMap = availableIndicatorService.listSubIndicatorsByIds(availableIndicators.stream().map(AvailableIndicatorsDO::getIndId).collect(Collectors.toList()));
        //把可计算的指标id set到对应的vo里
        Map<String, List<AvailableIndicatorVO>> collect = availableIndicators.stream().map(x -> {
            AvailableIndicatorVO indicator = availableIndicatorConverter.convert(x);
            //根据指标id拿出所有子指标的id
            List<Long> subIndicatorIds = indAndSubIndMap.get(indicator.getIndId());
            //如果子指标列表不为空并且不终止查询子指标，则继续列出子指标
            if (ObjectUtil.isNotNull(subIndicatorIds)) {
                indicator.setHasIndicatorList(subIndicatorIds);
                indicator.setIndFlag(true);
            }
            return indicator;
        }).collect(Collectors.groupingBy(AvailableIndicatorVO::getIndicatorClass));
        return CommonResult.success(collect);
    }

    @GetMapping("/listIndInds")
    @Operation(summary = "列出该指标下可计算的指标")
    @PreAuthenticated
    public CommonResult<Map<String, List<AvailableIndicatorVO>>> listIndInds(@Valid IndicatorsIndQueryParam param){
        List<AvailableIndicatorsDO> availableIndicatorsDOS = availableIndicatorService.listIndicators(param.getIndIds());
        Map<String, List<AvailableIndicatorVO>> result = availableIndicatorsDOS.stream().map(x->availableIndicatorConverter.convert(x)).collect(Collectors.groupingBy(AvailableIndicatorVO::getIndicatorClass));
        return CommonResult.success(result);
    }
}
