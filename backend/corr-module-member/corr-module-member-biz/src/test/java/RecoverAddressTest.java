import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import org.junit.Test;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

/**
 * @author dongchengye
 */
public class RecoverAddressTest {

    @Test
    public void test1() throws SignatureException {
        String gethSignPrefix = "CorrAi@";

        String originalMessageHashInHex="CorrAi@1729149428280";
        String signedHash="0xa0de0327e7f54fc3d0bcdb212954fde40d26ff636713c85c43f0f06ac0cc5d623e905e486dab5f3283ea8e9e5e1afaaf55793e2042078b7e71841f1719acd6f91b";

        byte[] messageHashBytes = Numeric.hexStringToByteArray(originalMessageHashInHex);
        String r = signedHash.substring(0, 66);
        String s = "0x" + signedHash.substring(66, 130);
        String v = "0x" + signedHash.substring(130, 132);
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
        System.out.println("Pubkey: " + pubkey);
        String address = Keys.getAddress(pubkey);
        System.out.println();
        System.out.println("address:" + address);
    }

    @Test
    public void test2(){
        String originalMessage="1729149428280";
        try {
            // 消息哈希（按 MetaMask 签名规则）
            String prefixedMessage = "\u0019Ethereum Signed Message:\n" + originalMessage.length() + originalMessage;
//            String prefixedMessage = "CorrAi@1729149428280";
            byte[] messageHash = Hash.sha3(prefixedMessage.getBytes());
            String signatureHex="0xa0de0327e7f54fc3d0bcdb212954fde40d26ff636713c85c43f0f06ac0cc5d623e905e486dab5f3283ea8e9e5e1afaaf55793e2042078b7e71841f1719acd6f91b";
            // 解析签名
            byte[] signatureBytes = Numeric.hexStringToByteArray(signatureHex);
            if (signatureBytes.length != 65) {
                throw new IllegalArgumentException("Invalid signature length");
            }

            // 将签名分成 r, s, v
            byte v = signatureBytes[64];
            if (v < 27) {
                v += 27;
            }

            byte[] r = new byte[32];
            byte[] s = new byte[32];
            System.arraycopy(signatureBytes, 0, r, 0, 32);
            System.arraycopy(signatureBytes, 32, s, 0, 32);

            // 使用 web3j 恢复公钥
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);
            BigInteger publicKeyRecovered = Sign.signedPrefixedMessageToKey(messageHash, signatureData);

            // 从公钥计算出地址
            String recoveredAddress = "0x" + Keys.getAddress(publicKeyRecovered);
            System.out.println(recoveredAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGithub(){
        String privateAccountKey = "503f38a9c967ed597e47fe25643985f032b072db8075426a92110f82df48dfcb";
        Credentials credentials = Credentials.create(privateAccountKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        //hexString matches web3.sha3 in Geth
        byte[] hash = Hash.sha3("This is a test".getBytes(StandardCharsets.UTF_8));
        String hexString = Numeric.toHexString(hash);

        //Concatenated R, S, and V variables match web3.personal.sign in Geth
        Sign.SignatureData signature = Sign.signPrefixedMessage(hash, ecKeyPair);
        byte[] retval = new byte[65];
        System.arraycopy(signature.getR(), 0, retval, 0, 32);
        System.arraycopy(signature.getS(), 0, retval, 32, 32);
        System.arraycopy(signature.getV(), 0, retval, 64, 1);
        System.out.println(Numeric.toHexString(retval));
    }
}
