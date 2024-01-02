package 代码积累库.redis;


import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTest {
    /**
     * <a href="https://blog.51cto.com/u_16099295/6472716">...</a>
     */
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    String key;
    String value;
    String hashKey;
    String hashValue;
    StringRedisTemplate stringRedisTemplate;
    RedisKeyValueTemplate redisKeyValueTemplate;

    @Test
    public void redis() {
        Boolean expire = redisTemplate.expire(key, 12, TimeUnit.SECONDS);
        Boolean hasKey = redisTemplate.hasKey(key);
        Boolean delete = redisTemplate.delete(key);
        Long expireSecond = redisTemplate.getExpire(key);

            redisTemplate.persist(key);// 一直坚持下去啊 移除过期时间

    }

    @Test
    public void string() {
        String s = redisTemplate.opsForValue().getAndPersist(key);
        redisTemplate.persist(key);
        redisTemplate.boundValueOps(key).persist();//跟上面一样,但上面感觉更好

        redisTemplate.opsForValue().get(key);//注意传null 会npe
        redisTemplate.opsForValue().set(key, value, 12, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfAbsent(key, value, 12, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfPresent(key, value, 21, TimeUnit.SECONDS);
        redisTemplate.opsForValue().multiSet(new HashMap<>() {{
            put(key, value);
        }});
        redisTemplate.opsForValue().multiSetIfAbsent(new HashMap<>() {{
            put(key, value);
        }});
        redisTemplate.opsForValue().increment(key);//如果该 key 不存在 将创建一个key 并赋值该 number
        redisTemplate.opsForValue().decrement(key);//如果 key 存在,但 value 不是 纯数字 ,将报错
    }



    @Test
    public void hash() {
        redisTemplate.opsForHash().putAll(key, new HashMap<>() {{
            put(1, 2);
        }});
        redisTemplate.opsForHash().entries(key);
        redisTemplate.opsForHash().get(key, 1);
        redisTemplate.opsForHash().delete(key, 2);
        redisTemplate.opsForHash().keys(key);
        redisTemplate.opsForHash().values(key);
    }

    @Test
    public void list() {
        List<String> range = redisTemplate.opsForList().range(key, 0, 1);//end -1表示尽头
        Long l1 = redisTemplate.opsForList().leftPush(key, "1");
        Long l = redisTemplate.opsForList().leftPush(key, "1", "2");//在指定位置插入数据
        String s = redisTemplate.opsForList().leftPop(key);//list 最左边的一个数据

        Long remove = redisTemplate.boundListOps(key).remove(1, "");
    }
    @Test
    public void set() {
        String s = redisTemplate.opsForSet().randomMember(key);
        List<String> strings = redisTemplate.opsForSet().randomMembers(key, 3);
        String pop = redisTemplate.opsForSet().pop(key);
        Long remove = redisTemplate.opsForSet().remove(key, 1, 2);
        Boolean move = redisTemplate.opsForSet().move(key, "v1", "key1");
        redisTemplate.opsForSet().size(key);
        Boolean member = redisTemplate.opsForSet().isMember(key, 1);
        redisTemplate.opsForSet().difference(key, "key1");
        redisTemplate.opsForSet().union(key, "key2");
    }
    @Test
    public void zset() {
        redisTemplate.opsForZSet().add(key, "1", 2);
    }
}