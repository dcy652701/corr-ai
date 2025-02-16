package corr.ai.module.metadata.converter.coin;

import corr.ai.module.metadata.controller.app.coin.resp.AvailableIntervalVO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIntervalDO;
import org.mapstruct.Mapper;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface IntervalConverter {
    AvailableIntervalVO convert(AvailableIntervalDO availableIntervalDO);
}
