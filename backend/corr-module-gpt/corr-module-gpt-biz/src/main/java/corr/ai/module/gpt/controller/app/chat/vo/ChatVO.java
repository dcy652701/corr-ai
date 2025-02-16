package corr.ai.module.gpt.controller.app.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatVO<T> {
    private String cacheKey;
    private List<T> data;
}
