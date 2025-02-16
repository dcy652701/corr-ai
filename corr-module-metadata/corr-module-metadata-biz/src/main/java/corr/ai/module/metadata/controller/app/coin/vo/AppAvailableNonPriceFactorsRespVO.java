package corr.ai.module.metadata.controller.app.coin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 可查询的列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppAvailableNonPriceFactorsRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25582")
    @ExcelProperty("主键")
    private Long foctorId;

    @Schema(description = "所属币的id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4796")
    @ExcelProperty("所属币的id")
    private Long belongCoinId;

    @Schema(description = "数据分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("数据分类")
    private String foctorType;

    @Schema(description = "数据名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("数据名称")
    private String foctorName;

    @Schema(description = "对应的数仓列名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("对应的数仓列名")
    private String foctorKeyName;

    @Schema(description = "最小的时间粒度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("最小的时间粒度")
    private String minInterval;

    @Schema(description = "通过该列能算出哪些指标")
    @ExcelProperty("通过该列能算出哪些指标")
    private List<Long> hasIndicatorsList;

    @Schema(description = "是否有可聚合的指标")
    private boolean indFlag;

    @Schema(description = "非价格数据的表，最后拼接时间间隔")
    @ExcelProperty("非价格数据的表，最后拼接时间间隔")
    private String nonPriceTable;

    @Schema(description = "是否是复杂json的非价格数据，如果是价格数据也为false", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否是复杂json的非价格数据，如果是价格数据也为false")
    private Boolean complexNonPriceFactor;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "字段类型")
    private String source;

}
