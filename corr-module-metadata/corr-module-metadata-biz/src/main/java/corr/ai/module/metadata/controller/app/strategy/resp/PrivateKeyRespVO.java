package corr.ai.module.metadata.controller.app.strategy.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
@AllArgsConstructor
public class PrivateKeyRespVO {
    private String url;
    private Long keyPairId;
}
