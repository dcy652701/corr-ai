package corr.ai.module.member.controller.app.vip.req.eventreq;

import corr.ai.module.member.controller.app.vip.req.activity.ActivityReq;
import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class EventReq {
    private String network;
    private List<ActivityReq> activity;
}
