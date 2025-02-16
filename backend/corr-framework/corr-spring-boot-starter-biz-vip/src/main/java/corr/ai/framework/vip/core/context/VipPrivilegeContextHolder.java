package corr.ai.framework.vip.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;

/**
 * @author dongchengye
 */
public class VipPrivilegeContextHolder {

    /**
     * 当前vip用户的数据权限与阈值，都写-1的话就表示这是一个免费用户
     */
    private static final ThreadLocal<Map<Long,Integer>> VIP_PRIVILEGE = new TransmittableThreadLocal<>();

    public static Map<Long,Integer> getPrivilegeThreshold(){
        return VIP_PRIVILEGE.get();
    }

    public static void setVipPrivilege(Map<Long,Integer> privileges){
        VIP_PRIVILEGE.set(privileges);
    }

    /**
     * 清空上下文
     */
    public static void clear(){
        VIP_PRIVILEGE.remove();
    }

}
