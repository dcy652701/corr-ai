package corr.ai.module.member.convert.whitelist;


import corr.ai.module.member.controller.admin.whitelist.req.AdminMemberUserWhiteListReq;
import corr.ai.module.member.dal.dataobject.whitelist.MemberUserWhiteListDO;
import org.mapstruct.Mapper;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface WhiteListConverter {

    MemberUserWhiteListDO convert(AdminMemberUserWhiteListReq req);
}
