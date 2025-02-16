package corr.ai.module.member.controller.admin.whitelist;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.member.controller.admin.whitelist.req.AdminMemberUserWhiteListReq;
import corr.ai.module.member.service.whitelist.MemberUserWhiteListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author dongchengye
 */
//@Tag(name = "管理后台 - 用户白名单")
//@RestController
//@RequestMapping("/member/white")
//@Validated
public class AdminWhiteListController {

//    @Autowired
    private MemberUserWhiteListService memberUserWhiteListService;

//    @PostMapping("/add")
//    @Operation(summary = "新增白名单")
//    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> addToWhiteList(@Valid @RequestBody AdminMemberUserWhiteListReq req){
        memberUserWhiteListService.addWhiteUser(req);
        return CommonResult.success(true);
    }
}
