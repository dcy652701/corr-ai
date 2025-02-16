package corr.ai.module.member.service.whitelist;

import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.member.controller.admin.whitelist.req.AdminMemberUserWhiteListReq;
import corr.ai.module.member.convert.whitelist.WhiteListConverter;
import corr.ai.module.member.dal.dataobject.whitelist.MemberUserWhiteListDO;
import corr.ai.module.member.dal.mysql.whitelist.MemberUserWhiteListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dongchengye
 */
@Service
public class MemberUserWhiteListServiceImpl implements MemberUserWhiteListService{

    @Autowired
    private MemberUserWhiteListMapper memberUserWhiteListMapper;

    @Autowired
    private WhiteListConverter whiteListConverter;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void addWhiteUser(AdminMemberUserWhiteListReq req) {
        MemberUserWhiteListDO convert = whiteListConverter.convert(req);
        memberUserWhiteListMapper.insert(convert);
    }

    @Override
    public boolean isInWhiteList(String email) {
        LambdaQueryWrapperX<MemberUserWhiteListDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(MemberUserWhiteListDO::getEmail,email);
        return memberUserWhiteListMapper.exists(wrapperX);
    }
}
