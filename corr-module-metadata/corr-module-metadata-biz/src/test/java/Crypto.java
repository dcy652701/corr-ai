import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * 测试非对称加密
 * @author dongchengye
 */
public class Crypto {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKeygenerated = keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());  // 将公钥转为Base64字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKeygenerated.getEncoded());  // 将私钥转为Base64字符串

        PrivateKey privateKey = getPrivateKeyFromString(privateKeyString);

        // 加密和解密的示例
        String originalString = "要加密的字符串";
        String encryptedString = encrypt(originalString, publicKey);
        String decryptedString = decrypt(encryptedString, privateKey);

        System.out.println("Original: " + originalString);
        System.out.println("Encrypted: " + encryptedString);
        System.out.println("Decrypted: " + decryptedString);

    }

    // 使用公钥加密
    public static String encrypt(String data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 使用私钥解密
    public static String decrypt(String encryptedData, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    // 从字符串中获取公钥
    public static PublicKey getPublicKeyFromString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedKey = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    // 从字符串中获取私钥
    public static PrivateKey getPrivateKeyFromString(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException {
        byte[] encodedKey = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
        return keyFactory.generatePrivate(keySpec);
    }
}
