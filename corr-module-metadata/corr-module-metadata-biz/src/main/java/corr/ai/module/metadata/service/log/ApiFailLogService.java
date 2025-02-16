package corr.ai.module.metadata.service.log;

import corr.ai.module.metadata.controller.app.get.req.ApiFailLogReq;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 失败日志记录
 *
 * @author dongchengye
 */
public interface ApiFailLogService {
    void insertLog(List<ApiFailLogReq> apiFailLogList, CountDownLatch countDownLatch);
}
