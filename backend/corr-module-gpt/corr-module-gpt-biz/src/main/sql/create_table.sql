-- 对话信息表
CREATE TABLE corr_gpt_chat_sessions
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '会话主键',
    user_id     BIGINT       NOT NULL COMMENT '用户编号，引用 member_user 表的主键',
    title       VARCHAR(255) NOT NULL COMMENT '会话标题',
    model       VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'gpt-4' COMMENT '使用的模型名称',
    creator     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    create_time DATETIME     NOT NULL                                        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    update_time DATETIME     NOT NULL                                        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     BIT(1)       NOT NULL                                        DEFAULT b'0' COMMENT '是否删除',
    tenant_id   BIGINT       NOT NULL                                        DEFAULT '0' COMMENT '租户编号',
    FOREIGN KEY (user_id) REFERENCES member_user(id) ON DELETE CASCADE,
    INDEX       idx_user_id (user_id),
    INDEX       idx_tenant_id (tenant_id),
    INDEX       idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='对话信息表';

-- 对话详情表
CREATE TABLE corr_gpt_chat_details
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '对话详情主键',
    session_id  BIGINT   NOT NULL COMMENT '会话编号',
    role        ENUM('user', 'assistant', 'system') NOT NULL COMMENT '消息角色',
    content     TEXT     NOT NULL COMMENT '消息内容',
    timestamp   DATETIME                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间戳',
    creator     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    create_time DATETIME NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater     VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    update_time DATETIME NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted     BIT(1)   NOT NULL                                            DEFAULT b'0' COMMENT '是否删除',
    tenant_id   BIGINT   NOT NULL                                            DEFAULT '0' COMMENT '租户编号',
    FOREIGN KEY (session_id) REFERENCES corr_gpt_chat_sessions (id) ON DELETE CASCADE,
    INDEX       idx_session_id (session_id),
    INDEX       idx_tenant_id (tenant_id),
    INDEX       idx_timestamp (timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='对话详情表';