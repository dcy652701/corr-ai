package corr.ai.module.system.service.logger;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.module.system.api.logger.dto.OperateLogCreateReqDTO;
import corr.ai.module.system.api.logger.dto.OperateLogPageReqDTO;
import corr.ai.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import corr.ai.module.system.dal.dataobject.logger.OperateLogDO;

/**
 * 操作日志 Service 接口
 *
 * @author CorrAi
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqDTO pageReqVO);

}
