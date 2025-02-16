package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;

import java.util.List;
import java.util.Map;

/**
 * @author dongchengye
 */
public interface AvailableIndicatorService {

    /**
     * 列出所有指标id
     *
     * @return
     */
    List<Long> listIndicatorIds();

    /**
     * 列出所有可查询的指标
     *
     * @return
     */
    List<AvailableIndicatorsDO> listIndicators(List<Long> indIds);

    /**
     * 为k线查询可计算的指标
     *
     * @param param
     * @return
     */
    List<AvailableIndicatorsDO> listKlineIndicators();

    /**
     * 根据指标id列出所有可计算的指标
     *
     * @param indIds
     * @return
     */
    Map<Long, List<Long>> listSubIndicatorsByIds(List<Long> indIds);
}
