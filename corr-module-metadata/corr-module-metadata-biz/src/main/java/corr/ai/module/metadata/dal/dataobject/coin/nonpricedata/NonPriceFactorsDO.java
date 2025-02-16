package corr.ai.module.metadata.dal.dataobject.coin.nonpricedata;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 非价格数据的查询结果
 *
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonPriceFactorsDO extends BaseDO {

    /**
     * 主键
     */
    private Long factorId;
    /**
     * 所属币的id
     */
    private Long belongCoinId;
    /**
     * 币的符号
     */
    private String symbol;
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
