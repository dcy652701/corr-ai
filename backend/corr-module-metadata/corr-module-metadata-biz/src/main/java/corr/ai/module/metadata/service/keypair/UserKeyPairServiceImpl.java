package corr.ai.module.metadata.service.keypair;

import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.metadata.dal.dataobject.keypair.UserKeyPairDO;
import corr.ai.module.metadata.dal.mysql.keypair.UserKeyPairMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongchengye
 */
@Service
public class UserKeyPairServiceImpl implements UserKeyPairService {

    @Autowired
    private UserKeyPairMapper userKeyPairMapper;

    @Override
    public void save(UserKeyPairDO userKeyPairDO) {
        userKeyPairMapper.insert(userKeyPairDO);
    }

    @Override
    public String getPublicKey() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        LambdaQueryWrapperX<UserKeyPairDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(UserKeyPairDO::getUserId, loginUserId);
        UserKeyPairDO userKeyPairDO = userKeyPairMapper.selectOne(wrapperX);
        return userKeyPairDO.getPublicKey();
    }

    @Override
    public String getPrivateKeyFileName() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        LambdaQueryWrapperX<UserKeyPairDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(UserKeyPairDO::getUserId, loginUserId);
        UserKeyPairDO userKeyPairDO = userKeyPairMapper.selectOne(wrapperX);
        return userKeyPairDO.getPrivateKeyFile();
    }

    @Override
    public boolean created() {
        return false;
    }
}
