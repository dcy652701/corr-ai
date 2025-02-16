package corr.ai.module.metadata.controller.app.coin.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import corr.ai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static corr.ai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 可查询的列分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppAvailableNonPriceFactorsPageReqVO extends PageParam {

    @Schema(description = "所属币的id", example = "4796")
    private Long belongCoinId;

    @Schema(description = "数据分类", example = "2")
    private String factorType;

    @Schema(description = "数据名称", example = "王五")
    private String factorName;

    @Schema(description = "对应的数仓列名", example = "李四")
    private String factorKeyName;

    @Schema(description = "最小的时间粒度")
    private String minInterval;

    @Schema(description = "通过该列能算出哪些指标")
    private String hasIndicators;

    @Schema(description = "非价格数据的表，最后拼接时间间隔")
    private String nonPriceTable;

    @Schema(description = "是否是复杂json的非价格数据，如果是价格数据也为false")
    private Boolean complexNonPriceFactor;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
