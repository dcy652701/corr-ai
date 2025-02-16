package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIntervalDO;

import java.util.List;

/**
 * 可用的interval
 *
 * @author dongchengye
 */
public interface AvailableIntervalService {
    /**
     * 列出所有的interval，因为数据不会很多，所以不用分页
     * @return
     */
    List<AvailableIntervalDO> listIntervals();
}
