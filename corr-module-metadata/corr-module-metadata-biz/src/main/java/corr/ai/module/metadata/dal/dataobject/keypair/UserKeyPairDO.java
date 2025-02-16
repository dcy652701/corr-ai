package corr.ai.module.metadata.dal.dataobject.keypair;

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
@TableName("corr_strategy_config")
public class UserKeyPairDO extends BaseDO {
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    private String privateKeyFile;
    private String privateKey;
    private String publicKey;
}
