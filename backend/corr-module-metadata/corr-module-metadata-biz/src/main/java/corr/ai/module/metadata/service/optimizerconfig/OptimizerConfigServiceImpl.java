package corr.ai.module.metadata.service.optimizerconfig;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import corr.ai.framework.tenant.core.context.TenantContextHolder;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerAndStrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerResultDO;
import corr.ai.module.metadata.dto.optimizer.OptimizerStatusDTO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import corr.ai.module.metadata.controller.app.optimizerconfig.vo.*;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerConfigDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.util.object.BeanUtils;

import corr.ai.module.metadata.dal.mysql.optimizerconfig.OptimizerConfigMapper;

import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.metadata.consts.ErrorConstants.OPTIMIZER_CONFIG_NOT_EXISTS;

/**
 * 优化器信息 Service 实现类
 *
 * @author CorrAi
 */
@Service
@Validated
public class OptimizerConfigServiceImpl implements OptimizerConfigService {

    @Resource
    private OptimizerConfigMapper optimizerConfigMapper;

    @Override
    public Long createOptimizerConfig(AppOptimizerConfigSaveReqVO createReqVO) {
        // 插入
        OptimizerConfigDO optimizerConfig = BeanUtils.toBean(createReqVO, OptimizerConfigDO.class);
        optimizerConfigMapper.insert(optimizerConfig);
        // 返回
        return optimizerConfig.getOid();
    }

    @Override
    public void updateOptimizerConfig(AppOptimizerConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateOptimizerConfigExists(updateReqVO.getOid());
        // 更新
        OptimizerConfigDO updateObj = BeanUtils.toBean(updateReqVO, OptimizerConfigDO.class);
        optimizerConfigMapper.updateById(updateObj);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean updateOptimizerStatus(OptimizerStatusDTO statusDTO) {
        UpdateWrapper<OptimizerConfigDO> wrapper = new UpdateWrapper<>();
        wrapper.set("stats",statusDTO.getStats());
        wrapper.set("final_percentage",statusDTO.getFinalPercentage());
        wrapper.eq("oid",statusDTO.getOptimizerId());
        TenantContextHolder.setTenantId(1L);
        optimizerConfigMapper.update(wrapper);
        return true;
    }

    @Override
    public void deleteOptimizerConfig(Long id) {
        // 校验存在
        validateOptimizerConfigExists(id);
        // 删除
        optimizerConfigMapper.deleteById(id);
    }

    private void validateOptimizerConfigExists(Long id) {
        if (optimizerConfigMapper.selectById(id) == null) {
            throw exception(OPTIMIZER_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public OptimizerConfigDO getOptimizerConfig(Long id) {
        return optimizerConfigMapper.selectById(id);
    }

    @Override
    public PageResult<OptimizerResultDO> getOptimizerConfigPage(AppOptimizerConfigPageReqVO pageReqVO) {
        return optimizerConfigMapper.selectOptimizerPage(pageReqVO);
    }

    @Override
    public OptimizerAndStrategyConfigDO getOptimizerInfoRpc(Long oid) {
        return optimizerConfigMapper.getOptimizerInfo(oid);
    }

}
