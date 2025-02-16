package corr.ai.module.metadata.controller.app.get.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 保密，所以用post请求来传递接口id
 *
 * @author dongchengye
 */
@Data
@Schema(description = "传递接口id来请求接口")
public class ApiReq {
    @Schema(description = "接口id")
    @NotNull(message = "接口id不能为空")
    private List<Long> apiId;
    @Schema(description = "币种")
    @NotNull(message = "币种符号不能为空")
    private String symbol;
}
