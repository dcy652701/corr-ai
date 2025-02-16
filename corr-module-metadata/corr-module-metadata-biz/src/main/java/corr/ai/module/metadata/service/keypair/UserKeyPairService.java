package corr.ai.module.metadata.service.keypair;

import corr.ai.module.metadata.dal.dataobject.keypair.UserKeyPairDO;

/**
 * @author dongchengye
 */
public interface UserKeyPairService {
    /**
     * 保存秘钥对
     *
     * @param userKeyPairDO
     */
    void save(UserKeyPairDO userKeyPairDO);

    /**
     * 获取公钥
     *
     * @return
     */
    String getPublicKey();

    /**
     * 获取私钥文件名
     *
     * @return
     */
    String getPrivateKeyFileName();

    /**
     * 判断用户是否生成过秘钥对
     *
     * @return
     */
    boolean created();
}
