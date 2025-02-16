package corr.ai.module.metadata.controller.app.coin.resp;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class AvailableIndicatorVO {
    private Long indId;
    private String indicatorClass;
    private String indicatorName;
    private JSONObject indicatorPayloadList;
    private String chartArea;
    private String chartType;
    private List<String> vreturn;
    private boolean indFlag;
    private List<Long> hasIndicatorList;
    private String indicatorType;
    private Integer useScene;
}
