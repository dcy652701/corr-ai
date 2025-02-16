package corr.ai.module.metadata.controller.app.coin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 可查询的列新增/修改 Request VO")
@Data
public class AppAvailableNonPriceFactorsSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25582")
    private Long factorId;

    @Schema(description = "所属币的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4796")
    @NotNull(message = "所属币的id不能为空")
    private Long belongCoinId;

    @Schema(description = "数据分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "数据分类不能为空")
    private String factorType;

    @Schema(description = "数据名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "数据名称不能为空")
    private String factorName;

    @Schema(description = "对应的数仓列名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "对应的数仓列名不能为空")
    private String factorKeyName;

    @Schema(description = "最小的时间粒度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "最小的时间粒度不能为空")
    private String minInterval;

    @Schema(description = "通过该列能算出哪些指标")
    private String hasIndicators;

    @Schema(description = "非价格数据的表，最后拼接时间间隔")
    private String nonPriceTable;

    @Schema(description = "是否是复杂json的非价格数据，如果是价格数据也为false", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否是复杂json的非价格数据，如果是价格数据也为false不能为空")
    private Boolean complexNonPriceFactor;

}
