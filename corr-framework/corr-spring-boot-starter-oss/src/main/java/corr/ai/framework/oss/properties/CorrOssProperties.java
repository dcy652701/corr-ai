package corr.ai.framework.oss.properties;

import corr.ai.framework.oss.enums.OssProvider;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * OSS配置类
 *
 * @author dongchengye
 */
@Validated
@ConfigurationProperties(prefix = "corr.oss")
@Data
public class CorrOssProperties {

    @Data
    @Valid
    public static class AliyunOssProperties {

        /**
         * Oss访问凭证ID
         */
        private String accessId;

        /**
         * Oss访问凭证密钥
         */
        private String accessKeySecret;

        /**
         * 访问域名地址
         */
        private String endpoint;

        /**
         * 存储桶名称
         */
        private String bucket;
    }


    @Data
    @Valid
    public static class TencentOssProperties {

        /**
         * Oss访问凭证ID
         */
        private String accessSecretId;

        /**
         * Oss访问凭证密钥
         */
        private String accessSecretKey;

        /**
         * 访问域名地址
         */
        private String region;

        /**
         * 存储桶名称
         */
        private String bucket;
    }


    /**
     * 是否启用OSS 默认为false
     */
    private boolean enable = false;

    /**
     * OSS提供者 比如 aliyun tencentcloud 等
     */
    private OssProvider provider;

    /**
     * 阿里云OSS配置
     */
    private AliyunOssProperties aliyun;

    /**
     * 腾讯云配置
     */
    private TencentOssProperties tencent;
}
