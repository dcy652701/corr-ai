package corr.ai.module.metadata.consts;

import corr.ai.framework.common.exception.ErrorCode;

/**
 * @author dongchengye
 */
public interface ErrorConstants {
    ErrorCode AVAILABLE_NON_PRICE_FACTORS_NOT_EXISTS = new ErrorCode(10017, "col not exist");
    ErrorCode OPTIMIZER_CONFIG_NOT_EXISTS = new ErrorCode(10018,"this optimizer not exist");
    ErrorCode CUSTOMIZE_LAYER_NOT_EXISTS = new ErrorCode(10019,"layer not exits");
}
