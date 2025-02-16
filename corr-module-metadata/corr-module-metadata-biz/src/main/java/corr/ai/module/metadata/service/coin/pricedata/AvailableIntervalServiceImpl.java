package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIntervalDO;
import corr.ai.module.metadata.dal.mysql.coin.pricedata.AvailableFoctorMapper;
import corr.ai.module.metadata.dal.mysql.coin.pricedata.AvailableIntervalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Service
public class AvailableIntervalServiceImpl implements AvailableIntervalService{

    @Autowired
    private AvailableIntervalMapper availableIntervalMapper;

    @Autowired
    private AvailableFoctorMapper availableFoctorMapper;

    @Override
    public List<AvailableIntervalDO> listIntervals() {
        List<String> collect = new ArrayList<>(availableFoctorMapper.listAvailableIntervals());
        return collect.stream().map(x -> {
            AvailableIntervalDO availableIntervalDO = new AvailableIntervalDO();
            availableIntervalDO.setIntervals(x);
            return availableIntervalDO;
        }).collect(Collectors.toList());
    }
}
