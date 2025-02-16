package corr.ai.module.member.controller.app.vip.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "创建订单后的回显信息")
public class VipOrderRespVO {
    @Schema(description = "订单id")
    private Long orderId;
    @Schema(description = "待支付金额")
    private BigDecimal needToPay;
    @Schema(description = "收款地址")
    private String walletAddress;
    @Schema(description = "待支付到期时间")
    private LocalDateTime orderExpireTime;
}
