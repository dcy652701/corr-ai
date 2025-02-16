package corr.ai.module.metadata.converter.strategy;

import cn.hutool.json.JSONUtil;
import corr.ai.module.metadata.controller.app.strategy.req.StrategyConfigReq;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyConfigRespVO;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyHistoryVO;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyPageRecordVO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyHistoryDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyPageDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring",imports = JSONUtil.class)
public interface StrategyConfigConverter {

    @Mappings({
            @Mapping(target = "priceDataQueryParam", expression = "java(JSONUtil.toJsonStr(req.getPriceDataQueryParam()))"),
            @Mapping(target = "nonPriceDataQueryParam", expression = "java(JSONUtil.toJsonStr(req.getNonPriceDataQueryParam()))")
    })
    StrategyConfigDO convert(StrategyConfigReq req);

    @Mappings({
            @Mapping(target = "img",expression = "java(pageDO.imgDownloadLink())"),
            @Mapping(target = "coinName",source = "symbol")
    })
    StrategyPageRecordVO convert(StrategyPageDO pageDO);

    @Mappings({
            @Mapping(target = "strategyConfig",expression = "java(configDO.recoverConfig())"),
            @Mapping(target = "priceDataQueryParam",expression = "java(configDO.getPriceDataQueryParamList())"),
            @Mapping(target = "nonPriceDataQueryParam",expression = "java(configDO.getNonPriceDataQueryParamList())")
    })
    StrategyConfigRespVO convert(StrategyConfigDO configDO);

    StrategyHistoryDO convertHis(StrategyConfigDO configDO);

    StrategyHistoryVO convert(StrategyHistoryDO historyDO);
}
