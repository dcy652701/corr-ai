package corr.ai.framework.vip.core.service;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.member.api.vip.VipApi;
import corr.ai.module.member.api.vip.VipPrivilegeDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static corr.ai.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;

/**
 * @author dongchengye
 */
@RequiredArgsConstructor
public class VipFrameworkServiceImpl implements VipFrameworkService {

    private final VipApi vipApi;


    /**
     * 针对 {@link #getAllVipAndPrivilege()} 的缓存
     */
    private final LoadingCache<Object, Map<Long,List<VipPrivilegeDTO>>> getVipPrivilegeCache = buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Object, Map<Long,List<VipPrivilegeDTO>>>() {
                @Override
                public Map<Long,List<VipPrivilegeDTO>> load(Object key) {
                    return vipApi.getAllVipAndPrivilege().getCheckedData();
                }

            });

    /**
     * 针对 {@link #validVip(Long)} 的缓存
     */
    private final LoadingCache<Long, CommonResult<Boolean>> validVipCache = buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Long, CommonResult<Boolean>>() {
                @Override
                public CommonResult<Boolean> load(Long userId) {
                    return vipApi.validVip(userId);
                }

            });

    @Override
    @SneakyThrows
    public Map<Long,List<VipPrivilegeDTO>> getAllVipAndPrivilege() {
        return getVipPrivilegeCache.get(Boolean.TRUE);
    }

    @Override
    @SneakyThrows
    public List<Long> validVip(Long userId) {
        // TODO 待实现验证逻辑，大致就是用mybatis-plus来拼接一些limit
        return null;
    }

    @Override
    @SneakyThrows
    public Map<Long, Integer> getPrivileges(Long vipId) {
        List<VipPrivilegeDTO> vipPrivilegeDTOS = getVipPrivilegeCache.get(Boolean.TRUE).get(vipId);
        Map<Long, Integer> collect = vipPrivilegeDTOS.stream().collect(Collectors.toMap(VipPrivilegeDTO::getPrivilegeId, VipPrivilegeDTO::getThreshold));
        return collect;
    }
}
