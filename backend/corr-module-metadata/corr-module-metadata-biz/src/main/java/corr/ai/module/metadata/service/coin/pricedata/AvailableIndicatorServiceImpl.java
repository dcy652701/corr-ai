package corr.ai.module.metadata.service.coin.pricedata;

import cn.hutool.core.util.ObjectUtil;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.SubIndicatorsDO;
import corr.ai.module.metadata.dal.mysql.coin.pricedata.AvailableIndicatorMapper;
import corr.ai.module.metadata.dal.mysql.coin.pricedata.SubIndicatorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Service
public class AvailableIndicatorServiceImpl implements AvailableIndicatorService {

    @Autowired
    private AvailableIndicatorMapper availableIndicatorMapper;

    @Autowired
    private SubIndicatorsMapper subIndicatorsMapper;

    @Override
    public List<Long> listIndicatorIds() {
        return availableIndicatorMapper.listAllIndicatorIds();
    }

    @Override
    public List<AvailableIndicatorsDO> listIndicators(List<Long> indIds) {
        return availableIndicatorMapper.listAvailableInds(indIds);
    }

    @Override
    public List<AvailableIndicatorsDO> listKlineIndicators() {
        return availableIndicatorMapper.listKlineIndicators();
    }

    @Override
    public Map<Long, List<Long>> listSubIndicatorsByIds(List<Long> indIds) {
        List<SubIndicatorsDO> subIndicators = subIndicatorsMapper.listSubIndByMainIndId(indIds);
        if (ObjectUtil.isNull(subIndicators) || subIndicators.isEmpty()) {
            return null;
        }
        Map<Long, List<Long>> result = new HashMap<>();
        //指标中计算的指标
        Map<Long, List<SubIndicatorsDO>> subIndMap = subIndicators.stream().collect(Collectors.groupingBy(SubIndicatorsDO::getMainIndId));
        for (Map.Entry<Long, List<SubIndicatorsDO>> entry : subIndMap.entrySet()) {
            //将实体类转换成子id
            List<Long> subIndIds = entry.getValue().stream().map(SubIndicatorsDO::getSubIndId).collect(Collectors.toList());
            result.put(entry.getKey(), subIndIds);
        }
        return result;
    }
}
