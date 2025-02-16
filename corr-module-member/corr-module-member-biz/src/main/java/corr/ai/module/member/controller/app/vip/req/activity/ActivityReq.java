package corr.ai.module.member.controller.app.vip.req.activity;

import corr.ai.module.member.controller.app.vip.req.raw.RawContract;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class ActivityReq {
    private String fromAddress;
    private String toAddress;
    private String blockNum;
    private String hash;
    private Float value;
    private String asset;
    private String category;
    private RawContract rawContract;
}
