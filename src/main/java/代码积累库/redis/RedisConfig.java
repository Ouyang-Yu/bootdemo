package 代码积累库.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching //开启缓存
@Configuration  //配置类
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {


        return  new RedisTemplate<>(){{
            setConnectionFactory(factory);
            //key序列化方式
            setKeySerializer(new StringRedisSerializer());
            //value序列化
            setValueSerializer(jsonRedisSerializer);
            //value hashmap序列化
            setHashValueSerializer(jsonRedisSerializer);
        }};
    }

    Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class) {{
        setObjectMapper(new ObjectMapper() {{
            setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//            enableDefaultTyping(DefaultTyping.NON_FINAL);
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }});

    }};
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {


        // 配置序列化（解决乱码的问题）,过期时间600秒
        return RedisCacheManager.builder(factory)
                .cacheDefaults(
                        RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                        .disableCachingNullValues()
                )
                .build();
    }

}