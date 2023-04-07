package 代码积累库.redis;

import org.junit.jupiter.api.Test;

public class redisBatch {
    @Test
    public void sda() {
//        String sql = "select code ,name from data_dictionary";
//        List<Dictionary> dictionaryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dictionary.class));
//
//
////        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
////        redisTemplate.executePipelined((RedisCallback<String>) connection -> {
////            dictionaryList.forEach(dictionary ->
////                    connection.set(
////                            stringSerializer.serialize("dic_" + dictionary.getCode()),
////                            //key加dic_前缀
////                            stringSerializer.serialize(dictionary.getName()),
////                            Expiration.seconds(60),
////                            RedisStringCommands.SetOption.UPSERT
////                    )
////            );
////            return null;
////        });//redis pipeline批处理新增(如果要expiration
//        String prefix = "dic_";
//        redisTemplate.opsForValue().multiSet(new HashMap<String,String>(){{
//            dictionaryList.forEach(dictionary -> put(prefix+dictionary.getName(),dictionary.getCode()));
//        }});//如果重复key,就更新value,如果不想更新就用multiSetIfPresent方法
//        //if expiration is no need ,multiSet is better
//    }
    }
}
