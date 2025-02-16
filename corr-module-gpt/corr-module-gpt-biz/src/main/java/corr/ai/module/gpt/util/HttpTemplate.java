package corr.ai.module.gpt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * @author dongchengye
 */
@Component
@Slf4j
public class HttpTemplate {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * get请求，返回响应实体（响应业务对象不支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables){
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * get请求，返回响应实体（响应业务对象支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T get(String url, Map<String, String> headers, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.GET, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，form表单提交（响应业务对象不支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param paramMap
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postByFrom(String url, Map<String, String> headers, Map<String, Object> paramMap, Class<T> responseType, Object... uriVariables){
        //指定请求头为表单类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(createBody(paramMap), httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，form表单提交（响应业务对象支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param paramMap
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postByFrom(String url, Map<String, String> headers, Map<String, Object> paramMap, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        //指定请求头为表单类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(createBody(paramMap), httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，json提交（响应业务对象不支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postByJson(String url, Map<String, String> headers, Object request, Class<T> responseType, Object... uriVariables){
        //指定请求头为json类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，json提交（响应业务对象支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param request
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T postByJson(String url, Map<String, String> headers, Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        //指定请求头为json类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，json提交，重定项
     * 支持restful风格
     * @param url
     * @param headers
     * @param request
     * @param uriVariables
     * @return
     */
    public String postForLocation(String url, Map<String, String> headers, Object request, Object... uriVariables){
        //指定请求头为json类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        URI uri = restTemplate.postForLocation(url, new HttpEntity<>(request, httpHeaders), uriVariables);
        if(Objects.nonNull(uri)){
            return uri.toString();
        }
        return null;
    }

    /**
     * put请求，json提交（响应业务对象不支持范型）
     * @param url
     * @param headers
     * @param request
     * @param uriVariables
     */
    public <T> T put(String url, Map<String, String> headers, Object request, Class<T> responseType, Object... uriVariables){
        //指定请求头为json类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.PUT, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * put请求，json提交（响应业务对象支持范型）
     * @param url
     * @param headers
     * @param request
     * @param uriVariables
     */
    public <T> T put(String url, Map<String, String> headers, Object request, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        //指定请求头为json类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.PUT, new HttpEntity<>(request, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * delete请求（响应业务对象不支持范型）
     * @param url
     * @param headers
     * @param uriVariables
     * @return
     */
    public <T> T delete(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables){
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.DELETE, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * delete请求（响应业务对象支持范型）
     * @param url
     * @param headers
     * @param uriVariables
     * @return
     */
    public <T> T delete(String url, Map<String, String> headers, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.DELETE, new HttpEntity<>(createHeaders(headers)), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，文件表单上传提交（响应业务对象不支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param paramMap
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T uploadFile(String url, Map<String, String> headers, MultiValueMap<String, Object> paramMap, Class<T> responseType, Object... uriVariables){
        //指定请求头为文件&表单类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(paramMap, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * post请求，文件表单上传提交（响应业务对象支持范型）
     * 支持restful风格
     * @param url
     * @param headers
     * @param paramMap
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> T uploadFile(String url, Map<String, String> headers, MultiValueMap<String, Object> paramMap, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        //指定请求头为文件&表单类型
        HttpHeaders httpHeaders = createHeaders(headers);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        ResponseEntity<T> rsp = commonExchange(url, HttpMethod.POST, new HttpEntity<>(paramMap, httpHeaders), responseType, uriVariables);
        return buildResponse(rsp);
    }

    /**
     * 公共http请求方法（响应业务对象不支持范型）
     * @param url
     * @param method
     * @param requestEntity
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> commonExchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables){
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }


    /**
     * 公共http请求方法（响应业务对象支持范型）
     * @param url
     * @param method
     * @param requestEntity
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> commonExchange(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables){
        return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables);
    }

    /**
     * 封装头部参数
     * @param headers
     * @return
     */
    private HttpHeaders createHeaders(Map<String, String> headers){
        return new HttpHeaders(){{
            if(headers != null && !headers.isEmpty()){
                headers.entrySet().forEach(item -> {
                    set(item.getKey(), item.getValue());
                });
            }
        }};
    }


    /**
     * 封装请求体
     * @param paramMap
     * @return
     */
    private MultiValueMap<String, Object> createBody(Map<String, Object> paramMap){
        MultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
        if(paramMap != null && !paramMap.isEmpty()){
            paramMap.entrySet().forEach(item -> {
                valueMap.add(item.getKey(), item.getValue());
            });
        }
        return valueMap;
    }

    /**
     * 返回响应对象
     * @param rsp
     * @param <T>
     * @return
     */
    private <T> T buildResponse(ResponseEntity<T> rsp){
        if(!rsp.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException(rsp.getStatusCode().getReasonPhrase());
        }
        return rsp.getBody();
    }
}
