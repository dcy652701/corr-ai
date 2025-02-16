package corr.ai.module.member.api.vip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * vip权益DTO
 *
 * @author dongchengye
 */
@Data
@Schema(description = "vip权益 DTP")
public class VipPrivilegeDTO {
    @Schema(description = "权益id，主键")
    private Long privilegeId;
    @Schema(description = "权益描述")
    private String description;
    @Schema(description = "权益阈值，比如月度会员只允许用1w条数据")
    private Integer threshold;
}
