package corr.ai.module.metadata.service.info;

import corr.ai.module.metadata.controller.app.get.req.ApiSaveReq;
import corr.ai.module.metadata.dal.dataobject.data.ApiDO;

import java.util.List;

/**
 * 数据接口信息
 *
 * @author dongchengye
 */
public interface ApiInfoService {
    /**
     * 保存所有可用的api
     *
     * @param reqs
     */
    void saveValidApis(List<ApiSaveReq> reqs);

    /**
     * 根据id拿到接口信息
     * @param apiId
     * @return
     */
    ApiDO getApiById(Long apiId);

    /**
     *
     * @param apiIds
     * @return
     */
    List<ApiDO> getApiListByIds(List<Long> apiIds);

    List<Long> listApiId(String sym);
}
