package corr.ai.module.metadata.framework;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongchengye
 */
@Configuration
public class TxOssConfig {
    @Value("${corr.oss.tencent.accessSecretId}")
    private String secretId;
    @Value("${corr.oss.tencent.accessSecretKey}")
    private String secretKey;
    @Value("${corr.oss.tencent.region}")
    private String region;

    @Bean
    public COSClient cosClient(){
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);

        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();

        // 设置 bucket 的地域
        // COS_REGION 请参见 https://cloud.tencent.com/document/product/436/6224
        // ap-hongkong
        clientConfig.setRegion(new Region(region));

        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
