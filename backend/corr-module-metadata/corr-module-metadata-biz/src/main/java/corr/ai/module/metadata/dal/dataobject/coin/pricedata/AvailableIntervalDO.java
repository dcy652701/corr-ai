package corr.ai.module.metadata.dal.dataobject.coin.pricedata;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据时间间隔的DO
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_available_intervals")
public class AvailableIntervalDO extends BaseDO {
    @TableId(value = "int_id",type = IdType.ASSIGN_ID)
    private Long indId;
    private String intervals;
}
