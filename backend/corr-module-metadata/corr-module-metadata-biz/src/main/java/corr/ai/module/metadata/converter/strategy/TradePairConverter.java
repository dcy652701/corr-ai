package corr.ai.module.metadata.converter.strategy;

import corr.ai.module.metadata.controller.app.strategy.resp.TradePairRespVO;
import corr.ai.module.metadata.dal.dataobject.strategy.TradePairDO;
import org.mapstruct.Mapper;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface TradePairConverter {
    TradePairRespVO convert(TradePairDO source);
}
