package corr.ai.module.metadata.dal.dataobject.coin.nonpricedata;

import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 非价格数据表对应的实体类
 *
 * @author dongchengye
 */
@TableName("corr_available_non_price_factors")
@KeySequence("corr_available_non_price_factors_seq")
// 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableNonPriceFactorsDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long factorId;
    /**
     * 所属币的id
     */
    private Long belongCoinId;
    /**
     * 数据分类
     */
    private String factorType;
    /**
     * 数据名称
     */
    private String factorName;
    /**
     * 对应的数仓列名
     */
    private String factorKeyName;
    /**
     * 最小的时间粒度
     */
    private String minInterval;
    /**
     * 通过该列能算出哪些指标
     */
    private String hasIndicators;
    /**
     * 非价格数据的表，最后拼接时间间隔
     */
    private String nonPriceTable;
    /**
     * 是否是复杂json的非价格数据，如果是价格数据也为false
     */
    private Boolean complexNonPriceFactor;

    /**
     * 把指标的id string转成long列表
     *
     * @return
     */
    public List<Long> getIndicators() {
        List<Long> indId = new ArrayList<>();
        if (hasIndicators.contains(",")) {
            List<Long> collect = Arrays.stream(hasIndicators.split(",")).map(Long::parseLong).collect(Collectors.toList());
            indId.addAll(collect);
            return indId;
        } else {
            indId.add(Long.parseLong(hasIndicators));
            return indId;
        }
    }

    /**
     * 是否有需要计算的指标
     *
     * @return
     */
    public boolean getIndFlag() {
        if (hasIndicators.contains(",")) {
            return true;
        } else {
            if (Integer.parseInt(hasIndicators) == 0) {
                //false表示没有可计算的指标
                return false;
            } else {
                return true;
            }
        }
    }

}
