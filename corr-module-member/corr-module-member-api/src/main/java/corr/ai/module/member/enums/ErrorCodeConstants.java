package corr.ai.module.member.enums;

import corr.ai.framework.common.exception.ErrorCode;

/**
 * Member 错误码枚举类
 * <p>
 * member 系统，使用 1-004-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 用户相关  1-004-001-000 ============
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_004_001_000, "User does not exist");
    ErrorCode USER_MOBILE_NOT_EXISTS = new ErrorCode(1_004_001_001, "Account does not exist");
    ErrorCode USER_MOBILE_USED = new ErrorCode(1_004_001_002, "Modify mobile failed，this number is used by ({})");
    ErrorCode USER_POINT_NOT_ENOUGH = new ErrorCode(1_004_001_003, "point is not enough");

    // ========== AUTH 模块 1-004-003-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_004_003_000, "Fail, account or password is not correct");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_004_003_001, "Fail，account is banned");
    ErrorCode AUTH_SOCIAL_USER_NOT_FOUND = new ErrorCode(1_004_003_005, "failed");
    ErrorCode AUTH_MOBILE_USED = new ErrorCode(1_004_003_007, "This mobile is existed");

    // ========== 用户收件地址 1-004-004-000 ==========
    ErrorCode ADDRESS_NOT_EXISTS = new ErrorCode(1_004_004_000, "Address invalid");

    //========== 用户标签 1-004-006-000 ==========
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_004_006_000, "User tag is invalid");
    ErrorCode TAG_NAME_EXISTS = new ErrorCode(1_004_006_001, "User tag is exist");
    ErrorCode TAG_HAS_USER = new ErrorCode(1_004_006_002, "operation failed");

    //========== 积分配置 1-004-007-000 ==========

    //========== Point Records 1-004-008-000 ==========
    ErrorCode POINT_RECORD_BIZ_NOT_SUPPORT = new ErrorCode(1_004_008_000, "User point record business type is not supported");

    //========== Sign-In Configuration 1-004-009-000 ==========
    ErrorCode SIGN_IN_CONFIG_NOT_EXISTS = new ErrorCode(1_004_009_000, "Sign-in day rule does not exist");
    ErrorCode SIGN_IN_CONFIG_EXISTS = new ErrorCode(1_004_009_001, "Sign-in day rule already exists");

    //========== Sign-In Records 1-004-010-000 ==========
    ErrorCode SIGN_IN_RECORD_TODAY_EXISTS = new ErrorCode(1_004_010_000, "Already signed in today, please do not repeat");

    //========== User Level 1-004-011-000 ==========
    ErrorCode LEVEL_NOT_EXISTS = new ErrorCode(1_004_011_000, "User level does not exist");
    ErrorCode LEVEL_NAME_EXISTS = new ErrorCode(1_004_011_001, "User level name [{}] is already in use");
    ErrorCode LEVEL_VALUE_EXISTS = new ErrorCode(1_004_011_002, "User level value [{}] is already used by [{}]");
    ErrorCode LEVEL_EXPERIENCE_MIN = new ErrorCode(1_004_011_003, "Upgrade experience must be greater than the upgrade experience [{}] set by the previous level [{}]");
    ErrorCode LEVEL_EXPERIENCE_MAX = new ErrorCode(1_004_011_004, "Upgrade experience must be less than the upgrade experience [{}] set by the next level [{}]");
    ErrorCode LEVEL_HAS_USER = new ErrorCode(1_004_011_005, "There are users in this level, it cannot be deleted");

    ErrorCode EXPERIENCE_BIZ_NOT_SUPPORT = new ErrorCode(1_004_011_201, "User experience business type is not supported");

    //========== User Group 1-004-012-000 ==========
    ErrorCode GROUP_NOT_EXISTS = new ErrorCode(1_004_012_000, "User group does not exist");
    ErrorCode GROUP_HAS_USER = new ErrorCode(1_004_012_001, "There are users in this group, it cannot be deleted");
    ErrorCode USER_WHITELIST_NOT_EXISTS = new ErrorCode(1_004_012_002,"User white list not exist");

}
