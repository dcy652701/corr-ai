package corr.ai.module.metadata.service.coin.nonpricedata;

import java.util.*;
import javax.validation.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsPageReqVO;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsSaveReqVO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.AvailableNonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;

/**
 * 可查询的列 Service 接口
 *
 * @author dongchengye
 */
public interface AvailableNonPriceFactorsService {

    /**
     * 列出所有factor，没必要分页
     *
     * @return
     */
    List<AvailableNonPriceFactorsDO> listNonPriceFactor(Long coinId, String intervals);

    /**
     * 创建可查询的列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAvailableNonPriceFactors(@Valid AppAvailableNonPriceFactorsSaveReqVO createReqVO);

    /**
     * 更新可查询的列
     *
     * @param updateReqVO 更新信息
     */
    void updateAvailableNonPriceFactors(@Valid AppAvailableNonPriceFactorsSaveReqVO updateReqVO);

    /**
     * 删除可查询的列
     *
     * @param id 编号
     */
    void deleteAvailableNonPriceFactors(Long id);

    /**
     * 获得可查询的列
     *
     * @param id 编号
     * @return 可查询的列
     */
    NonPriceFactorsDO getAvailableNonPriceFactors(Long id);

    /**
     * 获得可查询的列分页
     *
     * @param pageReqVO 分页查询
     * @return 可查询的列分页
     */
    PageResult<AvailableNonPriceFactorsDO> getAvailableNonPriceFactorsPage(AppAvailableNonPriceFactorsPageReqVO pageReqVO);

}
