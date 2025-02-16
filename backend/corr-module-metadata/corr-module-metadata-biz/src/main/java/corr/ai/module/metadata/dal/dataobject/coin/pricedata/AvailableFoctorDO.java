package corr.ai.module.metadata.dal.dataobject.coin.pricedata;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数仓中可查询的列
 *
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_available_foctors")
public class AvailableFoctorDO extends BaseDO {
    @TableId(value = "foctor_id", type = IdType.ASSIGN_ID)
    private Long foctorId;
    private Long belongCoinId;
    private String foctorType;
    private String foctorName;
    private String minInterval;
    private String hasIndicators;
    private String nonPriceTable;
    /**
     * 是否是复杂结构的非价格数据
     */
    private Boolean complexNonPriceFactor;

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
