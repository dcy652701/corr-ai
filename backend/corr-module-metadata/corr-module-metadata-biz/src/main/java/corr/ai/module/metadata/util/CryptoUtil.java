package corr.ai.module.metadata.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;

import javax.crypto.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * 加密与哈希工具
 *
 * @author dongchengye
 */
public class CryptoUtil {

    private static final String ALGORITHM = "RSA";

    private static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * 配置转换成hash
     *
     * @param input
     * @return
     */
    public static String applySha512(String input) {
        try {
            // Create MessageDigest instance for SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Perform the hash
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert byte array into signum representation
            return toHexString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not found", e);
        }
    }

    public static JSONObject generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKeygenerated = keyPair.getPrivate();
            // 将公钥转为Base64字符串
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            // 将私钥转为Base64字符串
            String privateKeyString = Base64.getEncoder().encodeToString(privateKeygenerated.getEncoded());
            JSONObject obj = JSONUtil.createObj();
            obj.set("public", publicKeyString);
            obj.set("private", privateKeyString);
            return obj;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10008, "创建密钥对失败"));
        }
    }

    // 使用公钥加密
    public static String encrypt(String data, PublicKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10009, "加密失败"));
        }
    }

    // 使用私钥解密
    public static String decrypt(String encryptedData, PrivateKey key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10010, "解密失败"));
        }
    }

    // 从字符串中获取公钥
    public static PublicKey getPublicKeyFromString(String key) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10011, "获取公钥失败"));
        }
    }

    // 从字符串中获取私钥
    public static PrivateKey getPrivateKeyFromString(String key) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new ServiceException(new ErrorCode(10012, "获取私钥失败"));
        }
    }

}
