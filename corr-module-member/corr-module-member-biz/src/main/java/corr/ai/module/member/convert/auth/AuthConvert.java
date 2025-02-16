package corr.ai.module.member.convert.auth;

import corr.ai.module.member.controller.app.auth.vo.*;
import corr.ai.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import corr.ai.module.member.controller.app.user.vo.AppMemberUserResetPasswordReqVO;
import corr.ai.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import corr.ai.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import corr.ai.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import corr.ai.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;
import corr.ai.module.system.api.social.dto.SocialUserBindReqDTO;
import corr.ai.module.system.api.social.dto.SocialUserUnbindReqDTO;
import corr.ai.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
import corr.ai.module.system.enums.sms.SmsSceneEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AppAuthSmsSendReqVO reqVO);
    SmsCodeUseReqDTO convert(AppMemberUserResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AppAuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean, String openid);

    SmsCodeValidateReqDTO convert(AppAuthSmsValidateReqVO bean);

    SocialWxJsapiSignatureRespDTO convert(SocialWxJsapiSignatureRespDTO bean);

}
