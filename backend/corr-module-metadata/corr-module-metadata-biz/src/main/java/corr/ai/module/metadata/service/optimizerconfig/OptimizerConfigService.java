package corr.ai.module.metadata.service.optimizerconfig;

import javax.validation.*;

import corr.ai.module.metadata.controller.app.optimizerconfig.vo.*;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerAndStrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerConfigDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerResultDO;
import corr.ai.module.metadata.dto.optimizer.OptimizerStatusDTO;

/**
 * 优化器信息 Service 接口
 *
 * @author CorrAi
 */
public interface OptimizerConfigService {

    /**
     * 创建优化器信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOptimizerConfig(@Valid AppOptimizerConfigSaveReqVO createReqVO);

    /**
     * 更新优化器信息
     *
     * @param updateReqVO 更新信息
     */
    void updateOptimizerConfig(@Valid AppOptimizerConfigSaveReqVO updateReqVO);

    /**
     * 只更新当前优化器的状态
     *
     * @param statusDTO
     * @return
     */
    boolean updateOptimizerStatus(OptimizerStatusDTO statusDTO);

    /**
     * 删除优化器信息
     *
     * @param id 编号
     */
    void deleteOptimizerConfig(Long id);

    /**
     * 获得优化器信息
     *
     * @param id 编号
     * @return 优化器信息
     */
    OptimizerConfigDO getOptimizerConfig(Long id);

    /**
     * 获得优化器信息分页
     *
     * @param pageReqVO 分页查询
     * @return 优化器信息分页
     */
    PageResult<OptimizerResultDO> getOptimizerConfigPage(AppOptimizerConfigPageReqVO pageReqVO);

    /**
     * rpc调用，获取策略与优化器配置信息
     *
     * @param oid
     * @return
     */
    OptimizerAndStrategyConfigDO getOptimizerInfoRpc(Long oid);

}
