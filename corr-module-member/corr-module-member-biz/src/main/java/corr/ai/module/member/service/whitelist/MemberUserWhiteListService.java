package corr.ai.module.member.service.whitelist;

import corr.ai.module.member.controller.admin.whitelist.req.AdminMemberUserWhiteListReq;
import corr.ai.module.member.dal.dataobject.whitelist.MemberUserWhiteListDO;

/**
 * @author dongchengye
 */
public interface MemberUserWhiteListService {

    /**
     * 新增白名单
     * @param req
     */
    void addWhiteUser(AdminMemberUserWhiteListReq req);

    /**
     * 是否在白名单
     * @param email
     * @return
     */
    boolean isInWhiteList(String email);
}
