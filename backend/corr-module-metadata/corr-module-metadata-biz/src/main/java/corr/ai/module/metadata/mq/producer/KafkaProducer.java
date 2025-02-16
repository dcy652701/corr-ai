package corr.ai.module.metadata.mq.producer;

import corr.ai.framework.common.core.KeyValue;
import corr.ai.module.metadata.consts.KafkaTopicConstants;
import corr.ai.module.metadata.mq.dto.ApiFailLogDTO;
import corr.ai.module.metadata.mq.dto.CoinAssetDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * kafka生产者，需要发哪个topic就在下面加响应的public方法即可
 *
 * @author dongchengye
 */
@Slf4j
@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * 发送失败日志
     *
     * @param apiFailLogDTO
     */
    public void sendFailLong(ApiFailLogDTO apiFailLogDTO) {
        kafkaTemplate.send(KafkaTopicConstants.API_FAIL_LOG, apiFailLogDTO);
    }

    public void sendCoinAsset(CoinAssetDTO coinAssetDTO){
        kafkaTemplate.send(KafkaTopicConstants.COIN_ASSET, coinAssetDTO);
    }


}
