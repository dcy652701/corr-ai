package corr.ai.module.gpt.controller.app.chat.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long sessionId;
    private Long userId;
    private String title;
    private String model;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
