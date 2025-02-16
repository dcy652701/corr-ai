package corr.ai.module.member.service.userwhitelist;

import java.util.*;
import javax.validation.*;
import corr.ai.module.member.controller.admin.userwhitelist.vo.*;
import corr.ai.module.member.dal.dataobject.userwhitelist.UserWhitelistDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;

/**
 * 可注册的白名单 Service 接口
 *
 * @author 芋道源码
 */
public interface UserWhitelistService {

    /**
     * 创建可注册的白名单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserWhitelist(@Valid UserWhitelistSaveReqVO createReqVO);

    /**
     * 更新可注册的白名单
     *
     * @param updateReqVO 更新信息
     */
    void updateUserWhitelist(@Valid UserWhitelistSaveReqVO updateReqVO);

    /**
     * 删除可注册的白名单
     *
     * @param id 编号
     */
    void deleteUserWhitelist(Long id);

    /**
     * 获得可注册的白名单
     *
     * @param id 编号
     * @return 可注册的白名单
     */
    UserWhitelistDO getUserWhitelist(Long id);

    /**
     * 获得可注册的白名单分页
     *
     * @param pageReqVO 分页查询
     * @return 可注册的白名单分页
     */
    PageResult<UserWhitelistDO> getUserWhitelistPage(UserWhitelistPageReqVO pageReqVO);

}