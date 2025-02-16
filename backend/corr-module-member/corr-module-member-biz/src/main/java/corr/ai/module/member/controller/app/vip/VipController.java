package corr.ai.module.member.controller.app.vip;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.common.util.servlet.ServletUtils;
import corr.ai.framework.security.core.annotations.PreAuthenticated;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.framework.websocket.core.sender.WebSocketMessageSender;
import corr.ai.module.member.controller.app.vip.req.AlchemyReq;
import corr.ai.module.member.controller.app.vip.req.ApplyPaymentReq;
import corr.ai.module.member.controller.app.vip.req.VipOrderCreateReq;
import corr.ai.module.member.controller.app.vip.resp.VipOrderRespVO;
import corr.ai.module.member.enums.WebsocketTopic;
import corr.ai.module.member.properties.CorrWebhookIpWhiteListConfigProperties;
import corr.ai.module.member.service.vip.VipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author dongchengye
 */
@Tag(name = "用户 APP - vip")
@RestController
@RequestMapping("/member/vip")
@Validated
@Slf4j
public class VipController {

    @Autowired
    private CorrWebhookIpWhiteListConfigProperties webhookIpWhiteListConfigProperties;

    @Value("${corr.usdt.walletAddress}")
    private String walletAddress;

    @Value("${corr.usdt.fakeTime}")
    private Integer fakeTime;

    @Value(("${corr.usdt.webhookId}"))
    private String webhookId;

    @Autowired
    private VipService vipService;

    @Autowired
    private WebSocketMessageSender webSocketMessageSender;

    @PostMapping("/webhook")
    public CommonResult<Boolean> webhook(@RequestBody AlchemyReq req) {
        List<String> alchemyIps = webhookIpWhiteListConfigProperties.getUsdt().getAlchemy();
        String clientIp = ServletUtils.getClientIP();
        if (!alchemyIps.contains(clientIp)) {
            //如果请求的ip不是来自alchemy，不允许后续的操作
            return CommonResult.success(false);
        }
        if (!Objects.equals(webhookId, req.getWebhookId())) {
            //如果webhook id不是alchemy配置的id，说明是恶意请求
            return CommonResult.success(false);
        }
        String createdAt = req.getCreatedAt();
        // 解析字符串为ZonedDateTime对象
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(createdAt);
        // 转换为LocalDateTime对象，这里假设你想要的是系统默认时区的本地时间
        LocalDateTime userPayTime = zonedDateTime.toLocalDateTime();
        // 现在的系统时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取UTC的ZoneOffset
        ZoneOffset utcOffset = ZoneOffset.UTC;
        // 将LocalDateTime转换为带偏移量的ZonedDateTime，表示UTC时间
        LocalDateTime webhookReceiveTime = localDateTime.atOffset(utcOffset).toLocalDateTime();
        Duration duration = Duration.between(userPayTime, webhookReceiveTime);
        long durationMinutes = duration.toMinutes();

        if (durationMinutes > 5) {
            // 恶意攻击
            return CommonResult.success(false);
        }
        // 回调里无法插入订单号，所以只能用websocket主动通知
        JSONObject messageContent = JSONUtil.createObj();
        messageContent.set("balance", req.getEvent().getActivity());
        messageContent.set("status", "success");
        webSocketMessageSender.send(1, SecurityFrameworkUtils.getLoginUserId(), WebsocketTopic.PAY_SUCCESS, messageContent.toString());
        return CommonResult.success(true);
    }

    @PostMapping("/createOrder")
    @PreAuthenticated
    @Operation(summary = "创建一个待支付订单")
    public CommonResult<VipOrderRespVO> createToBePaidOrder(@Valid @RequestBody VipOrderCreateReq req) {
        VipOrderRespVO vipOrder = vipService.createVipOrder(req);
        return CommonResult.success(vipOrder);
    }

    @PutMapping("/applyPayment")
    @PreAuthenticated
    @Operation(summary = "前端收到支付成功的推送之后，点击完成支付时的请求")
    public CommonResult<Boolean> applyPayment(@Valid @RequestBody ApplyPaymentReq req) {
        vipService.applyPayment(req.getOrderId(), req.getUserPaidBalance());
        return CommonResult.success(true);
    }

}
