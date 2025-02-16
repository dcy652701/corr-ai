package corr.ai.module.infra.enums;

import corr.ai.framework.common.exception.ErrorCode;

/**
 * Infra 错误码枚举类
 *
 * infra 系统，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== Parameter Configuration 1-001-000-000 ==========
    ErrorCode CONFIG_NOT_EXISTS = new ErrorCode(1_001_000_001, "Parameter configuration does not exist");
    ErrorCode CONFIG_KEY_DUPLICATE = new ErrorCode(1_001_000_002, "Duplicate parameter configuration key");
    ErrorCode CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE = new ErrorCode(1_001_000_003, "Cannot delete parameter configuration of system-built type");
    ErrorCode CONFIG_GET_VALUE_ERROR_IF_VISIBLE = new ErrorCode(1_001_000_004, "Failed to retrieve parameter configuration, reason: invisible configuration");

    // ========== API Error Log 1-001-002-000 ==========
    ErrorCode API_ERROR_LOG_NOT_FOUND = new ErrorCode(1_001_002_000, "API error log does not exist");
    ErrorCode API_ERROR_LOG_PROCESSED = new ErrorCode(1_001_002_001, "API error log already processed");

    // ========= File Related 1-001-003-000 =================
    ErrorCode FILE_PATH_EXISTS = new ErrorCode(1_001_003_000, "File path already exists");
    ErrorCode FILE_NOT_EXISTS = new ErrorCode(1_001_003_001, "File does not exist");
    ErrorCode FILE_IS_EMPTY = new ErrorCode(1_001_003_002, "File is empty");

    // ========== Code Generator 1-001-004-000 ==========
    ErrorCode CODEGEN_TABLE_EXISTS = new ErrorCode(1_001_004_002, "Table definition already exists");
    ErrorCode CODEGEN_IMPORT_TABLE_NULL = new ErrorCode(1_001_004_001, "Imported table does not exist");
    ErrorCode CODEGEN_IMPORT_COLUMNS_NULL = new ErrorCode(1_001_004_002, "Imported fields do not exist");
    ErrorCode CODEGEN_TABLE_NOT_EXISTS = new ErrorCode(1_001_004_004, "Table definition does not exist");
    ErrorCode CODEGEN_COLUMN_NOT_EXISTS = new ErrorCode(1_001_004_005, "Field definition does not exist");
    ErrorCode CODEGEN_SYNC_COLUMNS_NULL = new ErrorCode(1_001_004_006, "Synchronized fields do not exist");
    ErrorCode CODEGEN_SYNC_NONE_CHANGE = new ErrorCode(1_001_004_007, "Synchronization failed, no changes detected");
    ErrorCode CODEGEN_TABLE_INFO_TABLE_COMMENT_IS_NULL = new ErrorCode(1_001_004_008, "Table comment in the database is not filled");
    ErrorCode CODEGEN_TABLE_INFO_COLUMN_COMMENT_IS_NULL = new ErrorCode(1_001_004_009, "Column comment ({}) in the database is not filled");
    ErrorCode CODEGEN_MASTER_TABLE_NOT_EXISTS = new ErrorCode(1_001_004_010, "Master table (id={}) definition does not exist, please check");
    ErrorCode CODEGEN_SUB_COLUMN_NOT_EXISTS = new ErrorCode(1_001_004_011, "Field (id={}) of the sub-table does not exist, please check");
    ErrorCode CODEGEN_MASTER_GENERATION_FAIL_NO_SUB_TABLE = new ErrorCode(1_001_004_012, "Master table code generation failed, reason: no sub-tables");

    // ========== File Configuration 1-001-006-000 ==========
    ErrorCode FILE_CONFIG_NOT_EXISTS = new ErrorCode(1_001_006_000, "File configuration does not exist");
    ErrorCode FILE_CONFIG_DELETE_FAIL_MASTER = new ErrorCode(1_001_006_001, "This file configuration cannot be deleted, reason: it is the master configuration, deletion will prevent file uploads");

    // ========== Data Source Configuration 1-001-007-000 ==========
    ErrorCode DATA_SOURCE_CONFIG_NOT_EXISTS = new ErrorCode(1_001_007_000, "Data source configuration does not exist");
    ErrorCode DATA_SOURCE_CONFIG_NOT_OK = new ErrorCode(1_001_007_001, "Data source configuration is incorrect and cannot be connected");

    // ========== Student 1-001-201-000 ==========
    ErrorCode DEMO01_CONTACT_NOT_EXISTS = new ErrorCode(1_001_201_000, "Demo contact does not exist");
    ErrorCode DEMO02_CATEGORY_NOT_EXISTS = new ErrorCode(1_001_201_001, "Demo category does not exist");
    ErrorCode DEMO02_CATEGORY_EXITS_CHILDREN = new ErrorCode(1_001_201_002, "Cannot delete because child demo categories exist");
    ErrorCode DEMO02_CATEGORY_PARENT_NOT_EXITS = new ErrorCode(1_001_201_003, "Parent demo category does not exist");
    ErrorCode DEMO02_CATEGORY_PARENT_ERROR = new ErrorCode(1_001_201_004, "Cannot set itself as the parent demo category");
    ErrorCode DEMO02_CATEGORY_NAME_DUPLICATE = new ErrorCode(1_001_201_005, "A demo category with this name already exists");
    ErrorCode DEMO02_CATEGORY_PARENT_IS_CHILD = new ErrorCode(1_001_201_006, "Cannot set a child demo category as the parent demo category");
    ErrorCode DEMO03_STUDENT_NOT_EXISTS = new ErrorCode(1_001_201_007, "Student does not exist");
    ErrorCode DEMO03_GRADE_NOT_EXISTS = new ErrorCode(1_001_201_008, "Student grade does not exist");
    ErrorCode DEMO03_GRADE_EXISTS = new ErrorCode(1_001_201_009, "Student grade already exists");


}
