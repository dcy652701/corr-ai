package corr.ai.framework.oss.service;

import java.io.ByteArrayInputStream;

/**
 * @author dongchengye
 */
public interface OssService {
    /**
     * 上传文件
     *
     * @param fileName   文件名称
     * @param fileStream 文件流
     * @param isPrivate  文件可读权限
     *                   PublicReadWrite 公共读写
     *                   PublicRead 公共读
     *                   Private 私有
     * @return 上传后的文件路径
     */
    String uploadFile(String fileName, ByteArrayInputStream fileStream);
}
