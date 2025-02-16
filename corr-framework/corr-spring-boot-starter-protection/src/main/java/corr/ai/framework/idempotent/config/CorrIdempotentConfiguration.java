package corr.ai.framework.idempotent.config;

import corr.ai.framework.idempotent.core.aop.IdempotentAspect;
import corr.ai.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import corr.ai.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import corr.ai.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import corr.ai.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import corr.ai.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import corr.ai.framework.redis.config.CorrRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = CorrRedisAutoConfiguration.class)
public class CorrIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
