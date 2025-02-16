package corr.ai.module.gpt.converter;

import corr.ai.module.gpt.controller.app.chat.vo.ChatDetail;
import corr.ai.module.gpt.controller.app.chat.vo.ChatSession;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatDetailsDO;
import corr.ai.module.gpt.dal.dataobject.chat.GptChatSessionsDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ChatConverter {
    @Mappings({
            @Mapping(target = "sessionId",expression = "java(gptChatSessionsDO.getId())")
    })
    ChatSession convert(GptChatSessionsDO gptChatSessionsDO);

    ChatDetail convert(GptChatDetailsDO gptChatDetailsDO);
}
