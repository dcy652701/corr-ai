package corr.ai.module.member.mq.consumer.wallet;

import corr.ai.module.coin.api.BlockchainApi;
import corr.ai.module.member.message.user.MemberUserCreateMessage;
import corr.ai.module.member.service.user.MemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: 董丞业
 * @CreateTime: 2025-02-14
 * @Description: 观察者模式-监听用户注册创建钱包
 * @Version: 1.0
 */
@Slf4j
@Component
public class UserCreateWalletConsumer {
    @Autowired
    private MemberUserService memberUserService;

    @Autowired
    private BlockchainApi blockchainApi;

    @EventListener
    public void handle(MemberUserCreateMessage message) {
        Long userId = message.getUserId();
        log.info("新用户"+userId+"注册");
        String address = blockchainApi.generateAddressForRegisterUser(userId).getCheckedData();
        memberUserService.updateWalletAddress(userId, address);
    }
}
