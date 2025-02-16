package corr.ai.module.metadata.service.coin.nonpricedata;

import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsPageReqVO;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsSaveReqVO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.AvailableNonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;
import corr.ai.module.metadata.dal.mysql.coin.nonpricedata.AvailableNonPriceFactorsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.util.object.BeanUtils;


import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.metadata.consts.ErrorConstants.AVAILABLE_NON_PRICE_FACTORS_NOT_EXISTS;

/**
 * 可查询的列 Service 实现类
 *
 * @author dongchengye
 */
@Service
@Validated
public class AvailableNonPriceFactorsServiceImpl implements AvailableNonPriceFactorsService {

    @Resource
    private AvailableNonPriceFactorsMapper availableNonPriceFactorsMapper;

    @Override
    public List<AvailableNonPriceFactorsDO> listNonPriceFactor(Long coinId, String intervals) {
        return availableNonPriceFactorsMapper.listNonPriceFactors(coinId, intervals);
    }

    @Override
    public Long createAvailableNonPriceFactors(AppAvailableNonPriceFactorsSaveReqVO createReqVO) {
        // 插入
        AvailableNonPriceFactorsDO availableNonPriceFactors = BeanUtils.toBean(createReqVO, AvailableNonPriceFactorsDO.class);
        availableNonPriceFactorsMapper.insert(availableNonPriceFactors);
        // 返回
        return availableNonPriceFactors.getFactorId();
    }

    @Override
    public void updateAvailableNonPriceFactors(AppAvailableNonPriceFactorsSaveReqVO updateReqVO) {
        // 校验存在
        validateAvailableNonPriceFactorsExists(updateReqVO.getFactorId());
        // 更新
        AvailableNonPriceFactorsDO updateObj = BeanUtils.toBean(updateReqVO, AvailableNonPriceFactorsDO.class);
        availableNonPriceFactorsMapper.updateById(updateObj);
    }

    @Override
    public void deleteAvailableNonPriceFactors(Long id) {
        // 校验存在
        validateAvailableNonPriceFactorsExists(id);
        // 删除
        availableNonPriceFactorsMapper.deleteById(id);
    }

    private void validateAvailableNonPriceFactorsExists(Long id) {
        if (availableNonPriceFactorsMapper.selectById(id) == null) {
            throw exception(AVAILABLE_NON_PRICE_FACTORS_NOT_EXISTS);
        }
    }

    @Override
    public NonPriceFactorsDO getAvailableNonPriceFactors(Long id) {
        return availableNonPriceFactorsMapper.selectNonPriceFactorById(id);
    }

    @Override
    public PageResult<AvailableNonPriceFactorsDO> getAvailableNonPriceFactorsPage(AppAvailableNonPriceFactorsPageReqVO pageReqVO) {
        return availableNonPriceFactorsMapper.selectPage(pageReqVO);
    }

}
