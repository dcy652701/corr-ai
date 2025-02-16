package corr.ai.module.metadata.dal.dataobject.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description: 币种数据，除了k线的其他数据
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_coin_asset_data")
public class CoinAssetDO extends BaseDO {
    @TableId(value = "data_id", type = IdType.ASSIGN_ID)
    private Long dataId;
    private Long t;
    private Long v;
    private String symbol;
    private String dataType;
    private String dataName;
    private String dataFrequency;
}
