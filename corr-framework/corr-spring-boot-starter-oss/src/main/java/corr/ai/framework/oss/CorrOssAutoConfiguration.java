package corr.ai.framework.oss;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import corr.ai.framework.oss.properties.CorrOssProperties;
import corr.ai.framework.oss.provider.tx.TencentOssServiceImpl;
import corr.ai.framework.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * OSS 自动配置类
 *
 * @author dongchengye
 */
@Slf4j
@AutoConfiguration
// 条件装配
@ConditionalOnProperty(prefix = "sl.oss", name = "enable", havingValue = "true")
@EnableConfigurationProperties({CorrOssProperties.class})
public class CorrOssAutoConfiguration {

    @Resource
    private CorrOssProperties ossProperties;

//    @Bean
//    @ConditionalOnProperty(prefix = "sl.oss", name = "provider", havingValue = "aliyun", matchIfMissing = true)
//    public OssService aliyunOssClient() {
//        log.info("Load Aliyun Oss Client");
//
//        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
//        conf.setSupportCname(true);
//        CorrOssProperties.AliyunOssProperties properties = ossProperties.getAliyun();
//        OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint(),
//                properties.getAccessId(), properties.getAccessKeySecret());
//        return new AliyunOssService(ossClient, properties);
//    }

    @Bean
    @ConditionalOnProperty(prefix = "corr.oss", name = "provider", havingValue = "tencent", matchIfMissing = true)
    public OssService ossService() {
        log.info("Load Tencent Oss Client");
        CorrOssProperties.TencentOssProperties tencentOssProperties = ossProperties.getTencent();
        // 设置用户身份信息。
        // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = tencentOssProperties.getAccessSecretId();
        String secretKey = tencentOssProperties.getAccessSecretKey();
        String region = tencentOssProperties.getRegion();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参见 https://cloud.tencent.com/document/product/436/6224
        // ap-hongkong
        clientConfig.setRegion(new Region(region));

        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        return new TencentOssServiceImpl(cosClient,tencentOssProperties);
    }
}
