package corr.ai.module.metadata.controller.app.get.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "接口请求body")
public class MetricsListReq {
    @Schema(description = "币种符号列表")
    @NotNull(message = "必须传入币种符号")
    private List<String> filterSymbols;
}
