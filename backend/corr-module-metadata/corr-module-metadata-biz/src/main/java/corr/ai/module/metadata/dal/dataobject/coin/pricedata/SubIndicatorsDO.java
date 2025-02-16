package corr.ai.module.metadata.dal.dataobject.coin.pricedata;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_sub_indicators")
public class SubIndicatorsDO extends BaseDO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 指标id
     */
    private Long mainIndId;
    /**
     * 可计算的指标id，也是来自corr_available_indicators里的id
     */
    private Long subIndId;
}
