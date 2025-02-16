package corr.ai.framework.mq.redis.config;

import corr.ai.framework.mq.redis.core.RedisMQTemplate;
import corr.ai.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import corr.ai.framework.redis.config.CorrRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author CorrAi
 */
@Slf4j
@AutoConfiguration(after = CorrRedisAutoConfiguration.class)
public class CorrRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
