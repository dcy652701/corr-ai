package corr.ai.module.metadata.dal.dataobject.optimizerconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 优化器信息 DO
 *
 * @author CorrAi
 */
@TableName("corr_optimizer_config")
@KeySequence("corr_optimizer_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptimizerConfigDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long oid;
    /**
     * 要优化的策略id
     */
    private Long strategyId;
    /**
     * coinId
     */
    private Long coinId;
    /**
     * 选择的优化器
     */
    private String optimizer;
    /**
     * 优化器配置
     */
    private String optimizerConfig;
    /**
     * 当前的状态
     * 0已完成,1运行中,2运行中断
     */
    private Integer stats;
    /**
     * 最终的百分比
     */
    private Integer finalPercentage;

}
