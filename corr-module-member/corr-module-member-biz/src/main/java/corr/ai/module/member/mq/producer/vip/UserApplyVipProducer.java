package corr.ai.module.member.mq.producer.vip;

import corr.ai.module.member.message.vip.CreateVipForUserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dongchengye
 */
@Slf4j
@Component
public class UserApplyVipProducer {

    @Resource
    private ApplicationContext applicationContext;

    public void notifyToApplyVipForUser(Long userId,Long vipId){
        CreateVipForUserMessage createVipForUserMessage = new CreateVipForUserMessage(userId, vipId);
        applicationContext.publishEvent(createVipForUserMessage);
    }

}
