package corr.ai.module.member.service.userwhitelist;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import corr.ai.module.member.controller.admin.userwhitelist.vo.*;
import corr.ai.module.member.dal.dataobject.userwhitelist.UserWhitelistDO;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.util.object.BeanUtils;

import corr.ai.module.member.dal.mysql.userwhitelist.UserWhitelistMapper;

import static corr.ai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static corr.ai.module.member.enums.ErrorCodeConstants.*;

/**
 * 可注册的白名单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class UserWhitelistServiceImpl implements UserWhitelistService {

    @Resource
    private UserWhitelistMapper userWhitelistMapper;

    @Override
    public Long createUserWhitelist(UserWhitelistSaveReqVO createReqVO) {
        // 插入
        UserWhitelistDO userWhitelist = BeanUtils.toBean(createReqVO, UserWhitelistDO.class);
        userWhitelistMapper.insert(userWhitelist);
        // 返回
        return userWhitelist.getWid();
    }

    @Override
    public void updateUserWhitelist(UserWhitelistSaveReqVO updateReqVO) {
        // 校验存在
        validateUserWhitelistExists(updateReqVO.getWid());
        // 更新
        UserWhitelistDO updateObj = BeanUtils.toBean(updateReqVO, UserWhitelistDO.class);
        userWhitelistMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserWhitelist(Long id) {
        // 校验存在
        validateUserWhitelistExists(id);
        // 删除
        userWhitelistMapper.deleteById(id);
    }

    private void validateUserWhitelistExists(Long id) {
        if (userWhitelistMapper.selectById(id) == null) {
            throw exception(USER_WHITELIST_NOT_EXISTS);
        }
    }

    @Override
    public UserWhitelistDO getUserWhitelist(Long id) {
        return userWhitelistMapper.selectById(id);
    }

    @Override
    public PageResult<UserWhitelistDO> getUserWhitelistPage(UserWhitelistPageReqVO pageReqVO) {
        return userWhitelistMapper.selectPage(pageReqVO);
    }

}
