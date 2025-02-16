package corr.ai.module.metadata.service.customizelayer;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.util.object.BeanUtils;
import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerPageReqVO;
import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerSaveReqVO;
import corr.ai.module.metadata.converter.customizelayer.CustomizeLayerConverter;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.module.metadata.dal.mysql.customizelayer.CustomizeLayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.metadata.consts.ErrorConstants.CUSTOMIZE_LAYER_NOT_EXISTS;

/**
 * 用户自定义指标层 Service 实现类
 *
 * @author CorrAi
 */
@Service
@Validated
public class CustomizeLayerServiceImpl implements CustomizeLayerService {

    @Resource
    private CustomizeLayerMapper customizeLayerMapper;

    @Autowired
    private CustomizeLayerConverter customizeLayerConverter;

    @Override
    public Long createCustomizeLayer(AppCustomizeLayerSaveReqVO createReqVO) {
        // 插入
        CustomizeLayerDO customizeLayer = customizeLayerConverter.convert(createReqVO);
        customizeLayerMapper.insert(customizeLayer);
        // 返回
        return customizeLayer.getLayerId();
    }

    @Override
    public void updateCustomizeLayer(AppCustomizeLayerSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomizeLayerExists(updateReqVO.getLayerId());
        // 更新
        CustomizeLayerDO updateObj = customizeLayerConverter.convert(updateReqVO);
        customizeLayerMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomizeLayer(Long id) {
        // 校验存在
        validateCustomizeLayerExists(id);
        // 删除
        customizeLayerMapper.deleteById(id);
    }

    private void validateCustomizeLayerExists(Long id) {
        if (customizeLayerMapper.selectById(id) == null) {
            throw exception(CUSTOMIZE_LAYER_NOT_EXISTS);
        }
    }

    @Override
    public CustomizeLayerDO getCustomizeLayer(Long id) {
        return customizeLayerMapper.selectById(id);
    }

    @Override
    public PageResult<CustomizeLayerDO> getCustomizeLayerPage(AppCustomizeLayerPageReqVO pageReqVO) {
        return customizeLayerMapper.selectPage(pageReqVO);
    }

}
