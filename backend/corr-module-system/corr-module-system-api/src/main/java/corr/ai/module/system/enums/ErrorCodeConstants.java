package corr.ai.module.system.enums;

import corr.ai.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1-002-000-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_002_000_000, "Login failed, incorrect username or password");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_002_000_001, "Login failed, account is disabled");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1_002_000_004, "Captcha code is incorrect, reason: {}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1_002_000_005, "Account is not bound, binding is required");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1_002_000_007, "Mobile number does not exist");

    // ========== 菜单模块 1-002-001-000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1_002_001_000, "A menu with this name already exists");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1_002_001_001, "Parent menu does not exist");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1_002_001_002, "Cannot set itself as the parent menu");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1_002_001_003, "Menu does not exist");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1_002_001_004, "There are submenus, cannot delete");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1_002_001_005, "The parent menu type must be a directory or a menu");

    // ========== 角色模块 1-002-002-000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1_002_002_000, "Role does not exist");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1_002_002_001, "A role named 【{}】 already exists");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1_002_002_002, "A role with code 【{}】 already exists");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1_002_002_003, "Cannot operate on roles with system built-in type");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1_002_002_004, "The role named 【{}】 has been disabled");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1_002_002_005, "The code 【{}】 cannot be used");

    // ========== 用户模块 1-002-003-000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1_002_003_000, "User account already exists");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1_002_003_001, "Mobile number already exists");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1_002_003_002, "Email already exists");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_003_003, "User does not exist");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_002_003_004, "Imported user data cannot be empty!");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1_002_003_005, "User password verification failed");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1_002_003_006, "The user named 【{}】 has been disabled");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1_002_003_008, "Failed to create user, reason: exceeded the maximum tenant quota ({})!");
    ErrorCode USER_IMPORT_INIT_PASSWORD = new ErrorCode(1_002_003_009, "Initial password cannot be empty");

    // ========== 部门模块 1-002-004-000 ==========
    ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1_002_004_000, "A department with this name already exists");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1_002_004_001, "Parent department does not exist");
    ErrorCode DEPT_NOT_FOUND = new ErrorCode(1_002_004_002, "Current department does not exist");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1_002_004_003, "There are sub-departments, cannot delete");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1_002_004_004, "Cannot set itself as the parent department");
    ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1_002_004_006, "Department ({}) is not enabled, cannot be selected");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1_002_004_007, "Cannot set a child department as the parent department");

    // ========== 岗位模块 1-002-005-000 ==========
    ErrorCode POST_NOT_FOUND = new ErrorCode(1_002_005_000, "The current position does not exist");
    ErrorCode POST_NOT_ENABLE = new ErrorCode(1_002_005_001, "Position ({}) is not enabled, cannot be selected");
    ErrorCode POST_NAME_DUPLICATE = new ErrorCode(1_002_005_002, "A position with this name already exists");
    ErrorCode POST_CODE_DUPLICATE = new ErrorCode(1_002_005_003, "A position with this identifier already exists");


    // ========== Dictionary Type 1-002-006-000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1_002_006_001, "The current dictionary type does not exist");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1_002_006_002, "The dictionary type is not enabled, cannot be selected");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1_002_006_003, "A dictionary type with this name already exists");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1_002_006_004, "A dictionary type with this type already exists");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1_002_006_005, "Cannot delete, this dictionary type still has dictionary data");

    // ========== Dictionary Data 1-002-007-000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1_002_007_001, "The current dictionary data does not exist");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1_002_007_002, "Dictionary data ({}) is not enabled, cannot be selected");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1_002_007_003, "A dictionary data with this value already exists");

    // ========== Notice Announcement 1-002-008-000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1_002_008_001, "The current notice announcement does not exist");

    // ========== SMS Channel 1-002-011-000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1_002_011_000, "SMS channel does not exist");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1_002_011_001, "SMS channel is not enabled, cannot be selected");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1_002_011_002, "Cannot delete, this SMS channel still has SMS templates");

    // ========== SMS Template 1-002-012-000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_012_000, "SMS template does not exist");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_012_001, "A SMS template with code 【{}】 already exists");
    ErrorCode SMS_TEMPLATE_API_ERROR = new ErrorCode(1_002_012_002, "SMS API template call failed, reason: {}");
    ErrorCode SMS_TEMPLATE_API_AUDIT_CHECKING = new ErrorCode(1_002_012_003, "SMS API template cannot be used, reason: under review");
    ErrorCode SMS_TEMPLATE_API_AUDIT_FAIL = new ErrorCode(1_002_012_004, "SMS API template cannot be used, reason: audit failed, {}");
    ErrorCode SMS_TEMPLATE_API_NOT_FOUND = new ErrorCode(1_002_012_005, "SMS API template cannot be used, reason: template not found");

    // ========== SMS Sending 1-002-013-000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1_002_013_000, "Mobile number does not exist");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_013_001, "Template parameter ({}) is missing");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_013_002, "SMS template does not exist");

    // ========== SMS Verification Code 1-002-014-000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1_002_014_000, "Verification code not found");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1_002_014_001, "Verification code has expired");
    ErrorCode SMS_CODE_USED = new ErrorCode(1_002_014_002, "Verification code has been used");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1_002_014_004, "Exceeded the maximum SMS sending limit per day");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1_002_014_005, "SMS sent too frequently");

    // ========== Tenant Information 1-002-015-000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1_002_015_000, "Tenant does not exist");
    ErrorCode TENANT_DISABLE = new ErrorCode(1_002_015_001, "The tenant named 【{}】 has been disabled");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1_002_015_002, "The tenant named 【{}】 has expired");
    ErrorCode TENANT_CAN_NOT_UPDATE_SYSTEM = new ErrorCode(1_002_015_003, "System tenant cannot be modified or deleted!");
    ErrorCode TENANT_NAME_DUPLICATE = new ErrorCode(1_002_015_004, "A tenant named 【{}】 already exists");
    ErrorCode TENANT_WEBSITE_DUPLICATE = new ErrorCode(1_002_015_005, "A tenant with the domain name 【{}】 already exists");

    // ========== Tenant Package 1-002-016-000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1_002_016_000, "Tenant package does not exist");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1_002_016_001, "The tenant is using this package. Please assign a new package to the tenant before trying to delete");
    ErrorCode TENANT_PACKAGE_DISABLE = new ErrorCode(1_002_016_002, "The tenant package named 【{}】 has been disabled");

    // ========== Social User 1-002-018-000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1_002_018_000, "Social authorization failed, reason: {}");
    ErrorCode SOCIAL_USER_NOT_FOUND = new ErrorCode(1_002_018_001, "Social authorization failed, user not found");

    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_PHONE_CODE_ERROR = new ErrorCode(1_002_018_200, "Failed to obtain phone number");
    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_QRCODE_ERROR = new ErrorCode(1_002_018_201, "Failed to obtain mini program QR code");
    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_SUBSCRIBE_TEMPLATE_ERROR = new ErrorCode(1_002_018_202, "Failed to obtain mini program subscription message template");
    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_SUBSCRIBE_MESSAGE_ERROR = new ErrorCode(1_002_018_203, "Failed to send mini program subscription message");
    ErrorCode SOCIAL_CLIENT_NOT_EXISTS = new ErrorCode(1_002_018_210, "Social client does not exist");
    ErrorCode SOCIAL_CLIENT_UNIQUE = new ErrorCode(1_002_018_211, "Social client configuration already exists");



    // ========== OAuth2 Client 1-002-020-000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1_002_020_000, "OAuth2 client does not exist");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1_002_020_001, "OAuth2 client ID already exists");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1_002_020_002, "OAuth2 client has been disabled");
    ErrorCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ErrorCode(1_002_020_003, "This authorization type is not supported");
    ErrorCode OAUTH2_CLIENT_SCOPE_OVER = new ErrorCode(1_002_020_004, "Authorization scope is too broad");
    ErrorCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ErrorCode(1_002_020_005, "Invalid redirect_uri: {}");
    ErrorCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ErrorCode(1_002_020_006, "Invalid client_secret: {}");

    // ========== OAuth2 Authorization 1-002-021-000 =========
    ErrorCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ErrorCode(1_002_021_000, "client_id mismatch");
    ErrorCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ErrorCode(1_002_021_001, "redirect_uri mismatch");
    ErrorCode OAUTH2_GRANT_STATE_MISMATCH = new ErrorCode(1_002_021_002, "state mismatch");

    // ========== OAuth2 Authorization 1-002-022-000 =========
    ErrorCode OAUTH2_CODE_NOT_EXISTS = new ErrorCode(1_002_022_000, "code does not exist");
    ErrorCode OAUTH2_CODE_EXPIRE = new ErrorCode(1_002_022_001, "code has expired");

    // ========== Mail Account 1-002-023-000 ==========
    ErrorCode MAIL_ACCOUNT_NOT_EXISTS = new ErrorCode(1_002_023_000, "Mail account does not exist");
    ErrorCode MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS = new ErrorCode(1_002_023_001, "Cannot delete, this mail account still has related mail templates");

    // ========== Mail Template 1-002-024-000 ==========
    ErrorCode MAIL_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_024_000, "Mail template does not exist");
    ErrorCode MAIL_TEMPLATE_CODE_EXISTS = new ErrorCode(1_002_024_001, "Mail template code({}) already exists");

    // ========== Mail Sending 1-002-025-000 ==========
    ErrorCode MAIL_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_025_000, "Template parameter ({}) is missing");
    ErrorCode MAIL_SEND_MAIL_NOT_EXISTS = new ErrorCode(1_002_025_001, "Mail account does not exist");

    // ========== In-App Notification Template 1-002-026-000 ==========
    ErrorCode NOTIFY_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_026_000, "In-app notification template does not exist");
    ErrorCode NOTIFY_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_026_001, "In-app notification template code【{}】already exists");

    // ========== In-App Notification Sending 1-002-028-000 ==========
    ErrorCode NOTIFY_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_028_000, "Template parameter ({}) is missing");


}
