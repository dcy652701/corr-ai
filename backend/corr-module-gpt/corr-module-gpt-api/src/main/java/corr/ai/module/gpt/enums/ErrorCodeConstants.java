package corr.ai.module.gpt.enums;

import corr.ai.framework.common.exception.ErrorCode;

/**
 * Gpt 错误码枚举类
 *
 * gpt 系统，使用 1-098-000-000 段
 */

public interface ErrorCodeConstants {
    ErrorCode CHAT_SESSIONS_NOT_EXISTS = new ErrorCode(1_098_000_001, "对话信息不存在");
    ErrorCode CHAT_DETAILS_NOT_EXISTS = new ErrorCode(1_098_000_002, "对话详情不存在");
}
