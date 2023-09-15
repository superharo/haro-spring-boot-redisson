package online.superh.haro.spring.boot.redisson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class HaroSpringBootRedissonApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void testStringSetKey() {

        stringRedisTemplate.opsForValue().set("haro", "shuaishuai");

    }

}
