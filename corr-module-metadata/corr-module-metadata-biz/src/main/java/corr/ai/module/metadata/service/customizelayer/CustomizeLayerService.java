package corr.ai.module.metadata.service.customizelayer;

import java.util.*;
import javax.validation.*;
import corr.ai.module.metadata.controller.app.customizelayer.vo.*;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;

/**
 * 用户自定义指标层 Service 接口
 *
 * @author CorrAi
 */
public interface CustomizeLayerService {

    /**
     * 创建用户自定义指标层
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCustomizeLayer(@Valid AppCustomizeLayerSaveReqVO createReqVO);

    /**
     * 更新用户自定义指标层
     *
     * @param updateReqVO 更新信息
     */
    void updateCustomizeLayer(@Valid AppCustomizeLayerSaveReqVO updateReqVO);

    /**
     * 删除用户自定义指标层
     *
     * @param id 编号
     */
    void deleteCustomizeLayer(Long id);

    /**
     * 获得用户自定义指标层
     *
     * @param id 编号
     * @return 用户自定义指标层
     */
    CustomizeLayerDO getCustomizeLayer(Long id);

    /**
     * 获得用户自定义指标层分页
     *
     * @param pageReqVO 分页查询
     * @return 用户自定义指标层分页
     */
    PageResult<CustomizeLayerDO> getCustomizeLayerPage(AppCustomizeLayerPageReqVO pageReqVO);

}