package corr.ai.framework.oss.provider.tx;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import corr.ai.framework.oss.properties.CorrOssProperties;
import corr.ai.framework.oss.service.OssService;

import java.io.ByteArrayInputStream;

/**
 * 腾讯云 OSS Service
 *
 * @author JiangJian
 */
public class TencentOssServiceImpl implements OssService {

    /**
     * 腾讯云的OSS对象
     */
    private final COSClient cosClient;

    /**
     * 相关配置
     */
    private final CorrOssProperties.TencentOssProperties properties;

    public TencentOssServiceImpl(COSClient cosClient, CorrOssProperties.TencentOssProperties properties) {
        this.cosClient = cosClient;
        this.properties = properties;
    }

    @Override
    public String uploadFile(String fileName, ByteArrayInputStream fileStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        // objectMetadata.setContentLength(fil);

        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), fileName, fileStream, objectMetadata);

        // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
        // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
        putObjectRequest.setStorageClass(StorageClass.Standard);

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
