package corr.ai.module.metadata.dal.dataobject.coin;

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
@TableName("corr_coin_metadata_info")
public class CoinMetadataInfoDO extends BaseDO {
    @TableId(value = "meta_id", type = IdType.ASSIGN_ID)
    private Long metaId;
    private String symbol;
    private String coinFullName;
    private String coinLogo;
    private Integer intervals;
    private String intervalUnit;
    private String dwTableName;
    private String tradePair;
}
