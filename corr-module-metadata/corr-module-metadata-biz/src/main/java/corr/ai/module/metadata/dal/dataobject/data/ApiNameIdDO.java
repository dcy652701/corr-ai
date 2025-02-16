package corr.ai.module.metadata.dal.dataobject.data;

import lombok.Data;

/**
 * 只存储api的id和name
 *
 * @author dongchengye
 */
@Data
public class ApiNameIdDO {
    private Long apiId;
    private String apiName;
}
