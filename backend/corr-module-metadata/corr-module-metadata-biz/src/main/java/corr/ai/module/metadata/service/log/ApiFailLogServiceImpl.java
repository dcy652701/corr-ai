package corr.ai.module.metadata.service.log;

import corr.ai.module.metadata.controller.app.get.req.ApiFailLogReq;
import corr.ai.module.metadata.converter.data.ApiFailLogConverter;
import corr.ai.module.metadata.dal.dataobject.data.ApiFailLogDO;
import corr.ai.module.metadata.dal.mysql.data.ApiFailLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-14
 * @Description: 失败日志服务
 * @Version: 1.0
 */
@Service
@Slf4j
public class ApiFailLogServiceImpl implements ApiFailLogService{

    @Autowired
    private ApiFailLogMapper apiFailLogMapper;

    @Autowired
    private ApiFailLogConverter converter;


    @Override
    @Async("asyncThreadPoolExecutor")
    public void insertLog(List<ApiFailLogReq> apiFailLogList, CountDownLatch countDownLatch) {
//        log.info("插入失败日志");
        List<ApiFailLogDO> collect = apiFailLogList.stream().map(x -> converter.convert(x)).collect(Collectors.toList());
        try {
            apiFailLogMapper.insertBatch(collect);
        }finally {
            countDownLatch.countDown();
            log.info("插入完成，剩余任务数："+countDownLatch.getCount());
        }
    }
}
