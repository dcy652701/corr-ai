package corr.ai.module.member.controller.app.vip.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "创建待支付订单的req")
public class VipOrderCreateReq {
    @Schema(description = "选择一个会员套餐")
    @NotNull(message = "会员id不能为空")
    private Long vipId;
    @Schema(description = "待支付金额")
    @NotNull(message = "待支付金额不能为空")
    private BigDecimal needToPay;
}
