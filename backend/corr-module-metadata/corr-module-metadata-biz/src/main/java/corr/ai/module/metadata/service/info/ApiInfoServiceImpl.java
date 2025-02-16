package corr.ai.module.metadata.service.info;

import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.controller.app.get.req.ApiSaveReq;
import corr.ai.module.metadata.converter.data.ApiInfoConverter;
import corr.ai.module.metadata.dal.dataobject.data.ApiDO;
import corr.ai.module.metadata.dal.dataobject.data.ApiNameIdDO;
import corr.ai.module.metadata.dal.mysql.data.ApiInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Service
public class ApiInfoServiceImpl implements ApiInfoService {

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Autowired
    private ApiInfoConverter converter;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveValidApis(List<ApiSaveReq> reqs) {
        List<ApiDO> apiList = reqs.stream().map(x -> converter.convert(x)).collect(Collectors.toList());
        apiInfoMapper.insertBatch(apiList);
    }

    @Override
    public ApiDO getApiById(Long apiId) {
        return apiInfoMapper.selectOne(ApiDO::getApiId, apiId);
    }

    @Override
    public List<ApiDO> getApiListByIds(List<Long> apiIds) {
        LambdaQueryWrapperX<ApiDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.in(ApiDO::getApiId,apiIds);
        return apiInfoMapper.selectList(wrapperX);
    }

    @Override
    public List<Long> listApiId(String sym) {
        List<ApiNameIdDO> apis = apiInfoMapper.getApiIdList(sym);
        return apis.stream().map(ApiNameIdDO::getApiId).collect(Collectors.toList());
    }
}
