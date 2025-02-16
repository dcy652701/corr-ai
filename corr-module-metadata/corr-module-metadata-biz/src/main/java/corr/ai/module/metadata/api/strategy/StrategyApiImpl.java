package corr.ai.module.metadata.api.strategy;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.api.StrategyApi;
import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerRespVO;
import corr.ai.module.metadata.converter.customizelayer.CustomizeLayerConverter;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.module.metadata.dto.strategy.StrategyBaseFactorAndIndicatorDTO;
import corr.ai.module.metadata.dto.strategy.StrategyConfigReqDTO;
import corr.ai.module.metadata.service.coin.pricedata.AvailableIndicatorService;
import corr.ai.module.metadata.service.customizelayer.CustomizeLayerService;
import corr.ai.module.metadata.service.strategy.config.StrategyConfigService;
import corr.ai.module.metadata.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@RestController
@Validated
@Slf4j
public class StrategyApiImpl implements StrategyApi {

    @Autowired
    private StrategyConfigService strategyConfigService;

    @Autowired
    private AvailableIndicatorService availableIndicatorService;

    @Autowired
    private CustomizeLayerService customizeLayerService;

    @Autowired
    private CustomizeLayerConverter customizeLayerConverter;

    @Override
    public CommonResult<Long> generateStrategyId(StrategyConfigReqDTO strategyConfigReqDTO) {
        String configSha512 = CryptoUtil.applySha512(strategyConfigReqDTO.getRawStrategy());
        Long coinId = strategyConfigReqDTO.getCoinId();
        log.info("获取到的coinId " + coinId);
        if (strategyConfigService.exitsByHash(configSha512)) {
            return CommonResult.success(strategyConfigService.getIdByHash(configSha512));
        } else {

            List<Long> indIds = strategyConfigReqDTO.getDistinctFactorAndIndicators()
                    .stream()
                    .filter(x -> (Objects.equals(x.getType(), "ind")) || Objects.equals(x.getType(), "kline_ind") || (Objects.equals(x.getType(), "non_price_ind")))
                    .map(StrategyBaseFactorAndIndicatorDTO::getIndId).collect(Collectors.toList());
            //通过配置里的所有指标id拿到指标信息，封装成map，key是id，v是指标信息
            Map<Long, AvailableIndicatorsDO> availableIndicatorsMap = ObjectUtil.isNotEmpty(indIds) ? availableIndicatorService.listIndicators(indIds).stream().collect(Collectors.toMap(AvailableIndicatorsDO::getIndId, x -> x)): new HashMap<>();

            //过滤出指标，然后根据factorId进行groupby
            Map<Long, List<JSONObject>> configIndMap = strategyConfigReqDTO.getDistinctFactorAndIndicators()
                    .stream()
                    .filter(x -> (Objects.equals(x.getType(), "ind")) || Objects.equals(x.getType(), "kline_ind") || (Objects.equals(x.getType(), "non_price_ind")))
                    .map(x -> {
                        JSONObject obj = JSONUtil.createObj();
                        obj.set("symbol", x.getSymbol());
                        obj.set("factor", x.getFoctor());
                        obj.set("factor_id", x.getFactorId());
                        obj.set("factor_source", x.getFoctorSourse());
                        obj.set("label", x.getFoctorInd());
                        obj.set("interval", x.getInterval());
                        obj.set("indicatorType", availableIndicatorsMap.get(x.getIndId()).getIndicatorType());
                        obj.set("payload", x.getIndPayload());
                        obj.set("vreturn", availableIndicatorsMap.get(x.getIndId()).getReturnList());
                        obj.set("chart_area", availableIndicatorsMap.get(x.getIndId()).getChartArea());
                        obj.set("chart_type", availableIndicatorsMap.get(x.getIndId()).getChartType());
                        obj.set("indId", x.getIndId());
                        obj.set("useScene",availableIndicatorsMap.get(x.getIndId()).getUseScene());
                        return obj;
                    }).collect(Collectors.groupingBy(x -> x.getLong("factor_id")));


            List<JSONObject> collect = strategyConfigReqDTO.getDistinctFactorAndIndicators()
                    .stream()
                    .filter(x -> (Objects.equals(x.getType(), "foctor")) || (Objects.equals(x.getType(), "non_price_factor"))
                            || (Objects.equals(x.getType(), "ind"))
                            || (Objects.equals(x.getType(), "non_price_ind"))
                            || Objects.equals(x.getType(), "kline_ind")
                            || Objects.equals(x.getType(), "layer")
                    )
                    .map(x -> {
                        JSONObject obj = JSONUtil.createObj();
                        obj.set("factor_id", x.getFactorId());
                        obj.set("symbol", x.getCoinId());
                        obj.set("factor_source", x.getFoctorSourse());
                        obj.set("interval", x.getInterval());
                        obj.set("factor", x.getFoctor());
                        obj.set("from",x.getFrom());
                        obj.set("factor_indicator", configIndMap.get(x.getFactorId()));
                        if (Objects.equals(x.getType(), "layer")){
                            JSONObject layerObj = JSONUtil.createObj();
                            layerObj.set("layerId",x.getFactorId());
                            CustomizeLayerDO customizeLayer = customizeLayerService.getCustomizeLayer(x.getFactorId());
                            AppCustomizeLayerRespVO layerConfig = customizeLayerConverter.convert(customizeLayer);
                            layerObj.set("layerConfig",layerConfig);
                            obj.set("factor_layer",layerObj);
                        }
                        return obj;
                    }).collect(Collectors.toList());

            return CommonResult.success(strategyConfigService.insertAnEmptyStrategy(configSha512,
                    coinId, strategyConfigReqDTO.getRawStrategy(),
                    strategyConfigReqDTO.getStrategyFactors(),
                    strategyConfigReqDTO.getIntervals(),
                    JSONUtil.toJsonStr(collect)));
        }
    }

    @Override
    public CommonResult<JSONObject> getStrategyConfigById(Long sid) {
        String rawStrategy = strategyConfigService.queryById(sid).getRawStrategy();
        JSONObject strategyConfig = JSONUtil.parseObj(rawStrategy);
        return CommonResult.success(strategyConfig);
    }
}
