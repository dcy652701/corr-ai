package corr.ai.module.metadata.controller.app.coin.param;

import corr.ai.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

/**
 * 查询指标的指标的参数
 * @author dongchengye
 */
@Data
public class IndicatorsIndQueryParam extends PageParam {
    private List<Long> indIds;
}
