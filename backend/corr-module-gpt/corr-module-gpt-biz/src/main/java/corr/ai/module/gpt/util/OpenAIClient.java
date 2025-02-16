package corr.ai.module.gpt.util;

import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import org.springframework.stereotype.Component;

//@Component
@Service
public class OpenAIClient {

//    private static final String API_URL = "https://api.openai.com/v1";

    // openai的
//    private static final String API_URL = "https://myoneapi.imta7d93ab42ebdfc1009aad48b14a6a10d.youshouldknowthebabelnovel.com/v1";
//    private static final String API_KEY = "sk-infJWp6aAvIuL8cQ95D2Fd9360Ae4f768c3b11EfE6E73855";

    // deepseek的
    private static final String API_URL = "http://registry.corrai.tech";
//    private static final String API_KEY = "sk-4472bdad8fa546d79b8c59ff0f50e960";

    private final RestTemplate restTemplate;

    @Autowired
    public OpenAIClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    /**
     * 调用 OpenAI 接口获取回复
     *
     * @param userMessage 用户输入内容
     * @param model   使用的模型名称（例如 gpt-3.5-turbo）
     * @return 模型回复
     */
    public String createChatCompletion(String userMessage, String model) {
        // 构造请求的URL
        String url = API_URL + "/generate_answer/";

        // 设置请求头
        HttpEntity<Map<String, Object>> requestEntity = getMapHttpEntity(userMessage, model);

        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            return parseResponse(responseEntity);

        } catch (HttpStatusCodeException e) {
            handleError(e);
            return null;
        }
    }

    public String chatCompletionsContinue(List<Map<String, String>> messages, String model) {
        // 构造请求的URL
        String url = API_URL + "/generate_answer/";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(API_KEY);

        // 2. 构造请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("chat_history", messages);

        // 3. 发送请求
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            // 4. 解析响应
            Map<String, Object> responseBody = responseEntity.getBody();
            if (responseBody == null || !responseBody.containsKey("answer")) {
                throw new IllegalStateException("Unexpected response format: " + responseEntity);
            }
//            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
//            if (choices.isEmpty()) {
//                throw new IllegalStateException("No choices returned by the API.");
//            }
//            Map<String, Object> firstChoice = choices.get(0);
//            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            return (String) responseBody.get("answer");

        } catch (HttpStatusCodeException e) {
            // 处理 API 调用错误
            String errorResponse = e.getResponseBodyAsString();
            throw new IllegalStateException("Failed to call GPT API: " + e.getStatusCode() + " - " + errorResponse, e);
        }
    }

    private static @NotNull HttpEntity<Map<String, Object>> getMapHttpEntity(String userMessage, String model) {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Content-Type", "application/json");

        // 设置请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        // 构造 chat_history（JDK 8 兼容写法）
        List<Map<String, String>> chatHistory = new ArrayList<>();
        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", userMessage);
        chatHistory.add(userMessageMap);
        requestBody.put("chat_history", chatHistory);
        return new HttpEntity<>(requestBody, headers);
    }

    private String parseResponse(ResponseEntity<Map> responseEntity) {
        Map<String, Object> responseBody = responseEntity.getBody();
        if (responseBody == null) {
            throw new ServiceException(new ErrorCode(30001, "Failed to call GPT API, response body is null"));
        }
        if (responseBody.containsKey("answer")) {
            return (String) responseBody.get("answer");
        }
        if (responseBody.containsKey("error")) {
            Map<String, Object> error = (Map<String, Object>) responseBody.get("error");
            if (error.containsKey("message")) {
                String errorMessage = (String) error.get("message");
                throw new ServiceException(new ErrorCode(30001, errorMessage));
            }
        }
        throw new ServiceException(new ErrorCode(30001, "Failed to call GPT API"));
    }

    private void handleError(HttpStatusCodeException e) {
        String responseBody = e.getResponseBodyAsString();
        int statusCode = e.getStatusCode().value();

        if (statusCode == 422) {
            throw new ServiceException(new ErrorCode(30002, "Invalid request: " + responseBody));
        } else {
            throw new ServiceException(new ErrorCode(30001, "API error: " + responseBody));
        }
    }

}
