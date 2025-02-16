package corr.ai.module.member.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import corr.ai.framework.common.enums.UserTypeEnum;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.framework.security.config.SecurityProperties;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.member.controller.app.auth.vo.*;
import corr.ai.module.member.convert.auth.AuthConvert;
import corr.ai.module.member.service.auth.MemberAuthService;
import corr.ai.module.member.service.auth.WalletLoginService;
import corr.ai.module.member.service.whitelist.MemberUserWhiteListService;
import corr.ai.module.system.api.social.SocialClientApi;
import corr.ai.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static corr.ai.framework.common.pojo.CommonResult.success;
import static corr.ai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 认证")
@RestController
@RequestMapping("/member/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private MemberAuthService authService;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private SecurityProperties securityProperties;

    @Autowired
    private WalletLoginService walletLoginService;

    @Autowired
    private MemberUserWhiteListService memberUserWhiteListService;

    @PostMapping("/login")
    @Operation(summary = "使用手机 + 密码登录")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/emailLogin")
    @Operation(summary = "邮箱登录")
    public CommonResult<AppAuthLoginRespVO> emailLogin(@RequestBody @Valid AppEmailAuthLoginReqVO reqVO){
        return success(authService.emailLogin(reqVO));
    }

    @GetMapping("/getNonce")
    @Operation(summary = "获取签名的nonce")
    public CommonResult<String> getNonce(){
        return success(walletLoginService.generateNonce());
    }

    @PostMapping("/walletLogin")
    @Operation(summary = "钱包登录")
    public CommonResult<AppAuthLoginRespVO> walletLogin(@RequestBody @Valid AppWalletAuthLoginReqVO reqVO){
        return success(authService.walletLogin(reqVO));
    }

    @PostMapping("/emailRegister")
    @Operation(summary = "邮箱注册")
    public CommonResult<AppAuthLoginRespVO> emailRegister(@Valid @RequestBody AppAuthEmailRegisterReqVO reqVO){
        if (memberUserWhiteListService.isInWhiteList(reqVO.getEmail())){
            return success(authService.emailRegister(reqVO));

        }else {
            return CommonResult.error(new ErrorCode(10001,"Your email address is not in white list. Please contact us at info@corr.ai"));
        }
    }

    @PostMapping("/getVerifyCode")
    @Operation(summary = "发送邮箱验证码")
    public CommonResult<Boolean> sendEmailCode(@Valid @RequestBody AppEmailSmsSendReqVO reqVO){
        return success(true);
    }

    @PostMapping("/valid-email-code")
    @Operation(summary = "验证邮箱验证码")
    public CommonResult<Boolean> verifyEmailCode(@Valid @RequestBody AppEmailSmsValidateReqVO reqVO){
        return success(true);
    }

    @PutMapping("/emailForgetPassword")
    @Operation(summary = "忘记密码")
    public CommonResult<Boolean> resetEmailLoginPassword(@Valid @RequestBody AppEmailResetPasswordReqVO reqVO){
        return success(true);
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    public CommonResult<AppAuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    // ========== 短信登录相关 ==========

    @PostMapping("/sms-login")
    @Operation(summary = "使用手机 + 验证码登录")
    public CommonResult<AppAuthLoginRespVO> smsLogin(@RequestBody @Valid AppAuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    @PostMapping("/send-sms-code")
    @Operation(summary = "发送手机验证码")
    public CommonResult<Boolean> sendSmsCode(@RequestBody @Valid AppAuthSmsSendReqVO reqVO) {
        authService.sendSmsCode(getLoginUserId(), reqVO);
        return success(true);
    }

    @PostMapping("/validate-sms-code")
    @Operation(summary = "校验手机验证码")
    public CommonResult<Boolean> validateSmsCode(@RequestBody @Valid AppAuthSmsValidateReqVO reqVO) {
        authService.validateSmsCode(getLoginUserId(), reqVO);
        return success(true);
    }

    // ========== 社交登录相关 ==========

    @GetMapping("/social-auth-redirect")
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    public CommonResult<String> socialAuthRedirect(@RequestParam("type") Integer type,
                                                   @RequestParam("redirectUri") String redirectUri) {
        return CommonResult.success(authService.getSocialAuthorizeUrl(type, redirectUri));
    }

    @PostMapping("/social-login")
    @Operation(summary = "社交快捷登录，使用 code 授权码", description = "适合未登录的用户，但是社交账号已绑定用户")
    public CommonResult<AppAuthLoginRespVO> socialLogin(@RequestBody @Valid AppAuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

    @PostMapping("/weixin-mini-app-login")
    @Operation(summary = "微信小程序的一键登录")
    public CommonResult<AppAuthLoginRespVO> weixinMiniAppLogin(@RequestBody @Valid AppAuthWeixinMiniAppLoginReqVO reqVO) {
        return success(authService.weixinMiniAppLogin(reqVO));
    }

    @PostMapping("/create-weixin-jsapi-signature")
    @Operation(summary = "创建微信 JS SDK 初始化所需的签名",
            description = "参考 https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html 文档")
    public CommonResult<SocialWxJsapiSignatureRespDTO> createWeixinMpJsapiSignature(@RequestParam("url") String url) {
        SocialWxJsapiSignatureRespDTO signature = socialClientApi.createWxMpJsapiSignature(
                UserTypeEnum.MEMBER.getValue(), url).getCheckedData();
        return success(AuthConvert.INSTANCE.convert(signature));
    }

}
