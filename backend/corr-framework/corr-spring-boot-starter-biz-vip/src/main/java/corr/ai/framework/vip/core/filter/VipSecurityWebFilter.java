package corr.ai.framework.vip.core.filter;

import corr.ai.framework.common.exception.enums.GlobalErrorCodeConstants;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.common.util.servlet.ServletUtils;
import corr.ai.framework.security.core.LoginUser;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.framework.vip.core.context.VipPrivilegeContextHolder;
import corr.ai.framework.vip.core.service.VipFrameworkService;
import corr.ai.framework.web.config.WebProperties;
import corr.ai.framework.web.core.filter.ApiRequestFilter;
import corr.ai.framework.web.core.handler.GlobalExceptionHandler;
import corr.ai.module.member.api.vip.VipPrivilegeDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author dongchengye
 */
@Slf4j
public class VipSecurityWebFilter extends ApiRequestFilter {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final VipFrameworkService vipFrameworkService;

    public VipSecurityWebFilter(WebProperties webProperties, GlobalExceptionHandler globalExceptionHandler, VipFrameworkService vipFrameworkService) {
        super(webProperties);
        this.globalExceptionHandler = globalExceptionHandler;
        this.vipFrameworkService = vipFrameworkService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            Long loginUserVipId = SecurityFrameworkUtils.getLoginUserVipId();
            Map<Long, Integer> privileges = vipFrameworkService.getPrivileges(loginUserVipId);
            VipPrivilegeContextHolder.setVipPrivilege(privileges);
        }else {
            log.error("[doFilterInternal] 用户未登录，无法获取vip权限");
            ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                    "请求的租户标识未传递，请进行排查"));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
