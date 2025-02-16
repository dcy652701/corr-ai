package corr.ai.module.member.message.vip;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 给用户创建vip
 *
 * @author dongchengye
 */
@Data
@AllArgsConstructor
public class CreateVipForUserMessage {
    private Long userId;
    private Long vipId;
}
