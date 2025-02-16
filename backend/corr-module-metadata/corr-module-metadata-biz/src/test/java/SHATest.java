import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

/**
 * @author dongchengye
 */
public class SHATest {
    public static String toHexString(byte[] hash) {
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

    public static void main(String[] args) {
        String originalString = "{\n" +
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
        String sha512hex = applySha512(originalString);
        System.out.println("The SHA-512 hash of '" + originalString + "' is:");
        System.out.println(sha512hex);
    }
}
