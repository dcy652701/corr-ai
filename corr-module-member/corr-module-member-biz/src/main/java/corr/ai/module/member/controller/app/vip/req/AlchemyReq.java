package corr.ai.module.member.controller.app.vip.req;

import corr.ai.module.member.controller.app.vip.req.eventreq.EventReq;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class AlchemyReq {
    private String webhookId;
    private String id;
    private String createdAt;
    private String type;
    private EventReq event;
}
