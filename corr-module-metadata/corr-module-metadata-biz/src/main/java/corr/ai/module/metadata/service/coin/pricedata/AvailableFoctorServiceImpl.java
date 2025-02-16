package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableFoctorDO;
import corr.ai.module.metadata.dal.mysql.coin.pricedata.AvailableFoctorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongchengye
 */
@Service
public class AvailableFoctorServiceImpl implements AvailableFoctorService {

    @Autowired
    private AvailableFoctorMapper availableFoctorMapper;

    @Override
    public List<AvailableFoctorDO> listFoctor(Long coinId, String interval) {
        return availableFoctorMapper.listFoctors(coinId,interval);
    }

    @Override
    public AvailableFoctorDO getFoctorById(Long foctorId) {
        LambdaQueryWrapperX<AvailableFoctorDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(AvailableFoctorDO::getFoctorId,foctorId);
        AvailableFoctorDO availableFoctorDO = availableFoctorMapper.selectOne(wrapperX);
        return availableFoctorDO;
    }
}
