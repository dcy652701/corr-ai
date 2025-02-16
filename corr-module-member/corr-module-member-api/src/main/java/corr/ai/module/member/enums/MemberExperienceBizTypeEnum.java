package corr.ai.module.member.enums;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 会员经验 - 业务类型
 *
 * @author owen
 */
@Getter
@AllArgsConstructor
public enum MemberExperienceBizTypeEnum {

    /**
     * 管理员调整、邀请新用户、下单、退单、签到、抽奖
     */
    ADMIN(0, "Admin Adjustment", "Admin adjustment gained {} experience", true),
    INVITE_REGISTER(1, "Invite Reward", "Inviting a friend gained {} experience", true),
    SIGN_IN(4, "Sign-In Reward", "Sign-in gained {} experience", true),
    LOTTERY(5, "Lottery Reward", "Lottery gained {} experience", true),
    ORDER_GIVE(11, "Order Reward", "Placing an order gained {} experience", true),
    ORDER_GIVE_CANCEL(12, "Order Reward (Full Order Cancellation)", "Canceling an order gained {} experience", false), // Cancellation of ORDER_GIVE
    ORDER_GIVE_CANCEL_ITEM(13, "Order Reward (Single Item Refund)", "Refunding an order gained {} experience", false), // Cancellation of ORDER_GIVE
    ;


    /**
     * 业务类型
     */
    private final int type;
    /**
     * 标题
     */
    private final String title;
    /**
     * 描述
     */
    private final String description;
    /**
     * 是否为扣减积分
     */
    private final boolean add;

    public static MemberExperienceBizTypeEnum getByType(Integer type) {
        return EnumUtil.getBy(MemberExperienceBizTypeEnum.class,
                e -> Objects.equals(type, e.getType()));
    }
}
