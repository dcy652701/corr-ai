package corr.ai.module.member.service.auth;

/**
 * @author dongchengye
 */
public interface WalletLoginService {

    /**
     * 生成nonce
     * @return
     */
    String generateNonce();

    /**
     * 通过signature恢复地址
     * @return
     */
    String recoverAddress(String signedHash, String originalMessageHashInHex, String walletAddress);
}
