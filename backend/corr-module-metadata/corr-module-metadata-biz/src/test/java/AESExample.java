import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {

    private static final String ALGORITHM = "AES";

    // 生成秘钥
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128, new SecureRandom()); // 使用128位秘钥长度
        return keyGenerator.generateKey();
    }

    // 加密
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // 解密
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }

    public static void main(String[] args) {
        try {
            // 生成秘钥
            SecretKey key = generateKey();
            String keyString = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("秘钥: " + keyString);

            // 加密
            String data = "{\n" +
                    "    \"coinId\": \"101\",\n" +
                    "    \"btSymbol\": \"btc\",\n" +
                    "    \"btSymbolType\": \"close\",\n" +
                    "    \"backtestStyle\": \"geometric\",\n" +
                    "    \"tradeStyle\": 1,\n" +
                    "    \"startFund\": 10000,\n" +
                    "    \"tradesFrees\": 0.03,\n" +
                    "    \"leverage\": 1,\n" +
                    "    \"uniqueId\": \"uniquen0a23ealoq\",\n" +
                    "    \"entriesLogicItems\": [\n" +
                    "        {\n" +
                    "            \"foctor_sourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"type\": \"foctor\",\n" +
                    "            \"data_id\": 4,\n" +
                    "            \"symbol\": \"btc\",\n" +
                    "            \"interval\": \"1h\",\n" +
                    "            \"foctor\": \"close\",\n" +
                    "            \"id\": 4,\n" +
                    "            \"symbolLabel\": \"btc\",\n" +
                    "            \"uniqueId\": \"unique5mbqa2gvc\",\n" +
                    "            \"order\": 1,\n" +
                    "            \"coinId\": \"101\",\n" +
                    "            \"foctorSourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"logic_symbol_>\",\n" +
                    "            \"type\": \"logic_symbol\",\n" +
                    "            \"MathSymbols\": \">\",\n" +
                    "            \"uniqueId\": \"uniquer4oblyoqe\",\n" +
                    "            \"order\": 2,\n" +
                    "            \"mathSymbols\": \">\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \"ind\",\n" +
                    "            \"symbol\": \"btc\",\n" +
                    "            \"interval\": \"1h\",\n" +
                    "            \"foctor\": \"close\",\n" +
                    "            \"foctor_ind\": \"SMA\",\n" +
                    "            \"foctor_sourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"id\": 4,\n" +
                    "            \"data_id\": \"4#SMA#id-qqws6kbpn\",\n" +
                    "            \"ind_payload\": {\n" +
                    "                \"timeperiod\": 30\n" +
                    "            },\n" +
                    "            \"sub_payload\": \"timeperiod\",\n" +
                    "            \"sub_payload_value\": 30,\n" +
                    "            \"symbolLabel\": \"btc\",\n" +
                    "            \"uniqueId\": \"uniquen0anealoq\",\n" +
                    "            \"order\": 3,\n" +
                    "            \"coinId\": \"101\",\n" +
                    "            \"foctorSourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"foctorInd\": \"SMA\",\n" +
                    "            \"indPayload\": {\n" +
                    "                \"timeperiod\": 30\n" +
                    "            },\n" +
                    "            \"subPayload\": \"timeperiod\",\n" +
                    "            \"subPayloadValue\": 30\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"exitsLogicItems\": [\n" +
                    "        {\n" +
                    "            \"foctor_sourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"type\": \"foctor\",\n" +
                    "            \"data_id\": 4,\n" +
                    "            \"symbol\": \"btc\",\n" +
                    "            \"interval\": \"1h\",\n" +
                    "            \"foctor\": \"close\",\n" +
                    "            \"id\": 4,\n" +
                    "            \"symbolLabel\": \"btc\",\n" +
                    "            \"uniqueId\": \"uniquef2z93ek8a\",\n" +
                    "            \"order\": 1,\n" +
                    "            \"coinId\": \"101\",\n" +
                    "            \"foctorSourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": \"logic_symbol_<\",\n" +
                    "            \"type\": \"logic_symbol\",\n" +
                    "            \"MathSymbols\": \"<\",\n" +
                    "            \"uniqueId\": \"uniquelyav9trx5\",\n" +
                    "            \"order\": 2,\n" +
                    "            \"mathSymbols\": \"<\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"type\": \"ind\",\n" +
                    "            \"symbol\": \"btc\",\n" +
                    "            \"interval\": \"1h\",\n" +
                    "            \"foctor\": \"close\",\n" +
                    "            \"foctor_ind\": \"SMA\",\n" +
                    "            \"foctor_sourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"id\": 4,\n" +
                    "            \"data_id\": \"4#SMA#id-qqws6kbpn\",\n" +
                    "            \"ind_payload\": {\n" +
                    "                \"timeperiod\": 30\n" +
                    "            },\n" +
                    "            \"sub_payload\": \"timeperiod\",\n" +
                    "            \"sub_payload_value\": 30,\n" +
                    "            \"symbolLabel\": \"btc\",\n" +
                    "            \"uniqueId\": \"uniquetqo6uyd6s\",\n" +
                    "            \"order\": 3,\n" +
                    "            \"coinId\": \"101\",\n" +
                    "            \"foctorSourse\": [\n" +
                    "                \"price_data\"\n" +
                    "            ],\n" +
                    "            \"foctorInd\": \"SMA\",\n" +
                    "            \"indPayload\": {\n" +
                    "                \"timeperiod\": 30\n" +
                    "            },\n" +
                    "            \"subPayload\": \"timeperiod\",\n" +
                    "            \"subPayloadValue\": 30\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"startDate\": \"2020-01-01T00:00:00.000Z\",\n" +
                    "    \"endDate\": \"2024-04-25T00:00:00.000Z\",\n" +
                    "    \"interval\": \"1h\"\n" +
                    "}";
            String encryptedData = encrypt(data, key);
            System.out.println("加密后的内容: " + encryptedData);

            // 解密
            String decryptedData = decrypt(encryptedData, key);
            System.out.println("解密后的内容: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
