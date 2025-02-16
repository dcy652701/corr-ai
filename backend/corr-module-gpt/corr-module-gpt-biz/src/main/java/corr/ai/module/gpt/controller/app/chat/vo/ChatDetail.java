package corr.ai.module.gpt.controller.app.chat.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ChatDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long sessionId;
    private String role;
    private String content;
    private LocalDateTime chatTimestamp;
}
