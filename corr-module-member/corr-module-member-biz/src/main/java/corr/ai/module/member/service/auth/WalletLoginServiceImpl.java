package corr.ai.module.member.service.auth;

import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 钱包登录服务，但是不经过链上校验
 *
 * @author dongchengye
 */
@Service
public class WalletLoginServiceImpl implements WalletLoginService {
    @Override
    public String generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        // 将字节序列转换为十六进制字符串
        String hexString = toHexString(randomBytes);
        return hexString;
    }

    @Override
    public String recoverAddress(String signedHash, String originalMessageHashInHex, String walletAddress) {
        String gethSignPrefix = "\u0019CorrAi@\n";
        try {
            byte[] messageHashBytes = Numeric.hexStringToByteArray(originalMessageHashInHex);
            String r = signedHash.substring(0, 66);
            String s = "0x"+signedHash.substring(66, 130);
            String v = "0x"+signedHash.substring(130, 132);
            System.out.println();
            byte[] msgBytes = new byte[gethSignPrefix.getBytes().length + messageHashBytes.length];
            byte[] prefixBytes = gethSignPrefix.getBytes();
            System.arraycopy(prefixBytes, 0, msgBytes, 0, prefixBytes.length);
            System.arraycopy(messageHashBytes, 0, msgBytes, prefixBytes.length, messageHashBytes.length);
            String pubkey = Sign.signedMessageToKey(msgBytes,
                            new Sign.SignatureData(Numeric.hexStringToByteArray(v)[0],
                                    Numeric.hexStringToByteArray(r),
                                    Numeric.hexStringToByteArray(s)))
                    .toString(16);
            System.out.println("");
            System.out.println("Pubkey: " + pubkey);
            String address = Keys.getAddress(pubkey);
            return address;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10006,"钱包签名解析错误"));
        }
    }

    /**
     * 辅助方法：将字节数组转换为十六进制字符串
     * @param bytes
     * @return
     */
    private static String toHexString(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        return bigInt.toString(16);
    }
}
