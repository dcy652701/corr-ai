package corr.ai.module.member.mq.consumer.vip;

import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.member.message.vip.CreateVipForUserMessage;
import corr.ai.module.member.service.user.MemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author dongchengye
 */
@Slf4j
@Component
public class UserApplyVipListener {

    @Autowired
    private MemberUserService memberUserService;

    @EventListener
    public void handle(CreateVipForUserMessage message){
        log.info("用户 "+ SecurityFrameworkUtils.getLoginUserId()+" 开通会员");

    }

}
