package corr.ai.module.metadata.controller.app.get.resp.sub;


import lombok.Data;

import java.util.List;

/**
 * 资产信息
 *
 * @author dongchengye
 */
@Data
public class Asset {
    private String symbol;
    private String name;
    private List<String> tags;
    private List<String> exchanges;
}
