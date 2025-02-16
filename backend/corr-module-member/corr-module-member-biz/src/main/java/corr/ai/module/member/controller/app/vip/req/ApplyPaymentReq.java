package corr.ai.module.member.controller.app.vip.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "确认支付")
public class ApplyPaymentReq {
    @Schema(description = "订单id")
    private Long orderId;
    @Schema(description = "用户支付金额")
    private BigDecimal userPaidBalance;
}
