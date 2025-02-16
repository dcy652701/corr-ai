package corr.ai.module.metadata.service.oss;

import java.io.ByteArrayInputStream;

/**
 * @author dongchengye
 */
public interface TxOssService {
    String uploadFile(String fileName, ByteArrayInputStream fileStream);
}
