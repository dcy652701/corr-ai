package corr.ai.module.metadata.controller.app.strategy.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 分页查询的结果
 *
 * @author dongchengye
 */
@Data
@AllArgsConstructor
public class StrategyPageResultVO {
    private Long total;
    private Map<Long, List<StrategyPageRecordVO>> result;
}
