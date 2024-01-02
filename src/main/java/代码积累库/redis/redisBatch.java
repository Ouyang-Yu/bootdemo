package 代码积累库.redis;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class redisBatch {

    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Resource
    JdbcTemplate jdbcTemplate;

    @Test
    public void sda() {
        String sql = "select code ,name from data_dictionary";
        List<Dictionary> dictionaryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dictionary.class));


       RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();

       redisTemplate.executePipelined((RedisCallback<String>) connection -> {
           dictionaryList.forEach(dictionary ->
                   connection.set(
                           stringSerializer.serialize("dic_" + dictionary.getCode()),
                           //key加dic_前缀
                           stringSerializer.serialize(dictionary.getName()),
                           Expiration.seconds(60),
                           RedisStringCommands.SetOption.UPSERT
                   )
           );
           return null;
       });//redis pipeline批处理新增(如果要expiration
        String prefix = "dic_";
        Map<String, String> codeNameMap = dictionaryList.stream().collect(Collectors.toMap(Dictionary::getCode, Dictionary::getName));
        redisTemplate.opsForValue()
                .multiSet(codeNameMap);// 如果重复key,就更新value,如果不想更新就用multiSetIfPresent方法
        // if expiration is no need ,multiSet is better
    }
    @Data
    class Dictionary {
        String name;
        String code;
    }
}

