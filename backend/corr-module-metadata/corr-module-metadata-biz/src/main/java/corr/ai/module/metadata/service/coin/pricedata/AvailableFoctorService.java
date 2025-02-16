package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableFoctorDO;

import java.util.List;

/**
 * 数仓中可查询的列
 *
 * @author dongchengye
 */
public interface AvailableFoctorService {
    /**
     * 没多少，没必要分页
     *
     * @return
     */
    List<AvailableFoctorDO> listFoctor(Long coinId, String interval);

    /**
     * 根据id查询foctor
     *
     * @param foctorId
     * @return
     */
    AvailableFoctorDO getFoctorById(Long foctorId);
}
