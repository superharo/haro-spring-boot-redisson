package online.superh.haro.spring.boot.redisson.lock;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @version: 1.0
 * @author: haro
 * @description:
 * @date: 2023-09-15 16:14
 */
@SpringBootTest
public class LockTest {

    private static final String LOCK_KEY = "anylock";

    @Autowired // <1>
    private RedissonClient redissonClient;

    @Test
    public void test() throws InterruptedException {
        // 启动一个线程 A ，去占有锁
        new Thread(() -> {
            // 加锁以后 10 秒钟自动解锁
            // 无需调用 unlock 方法手动解锁
            final RLock lock = redissonClient.getLock(LOCK_KEY);
            lock.lock(10, TimeUnit.SECONDS);
        }, "A").start();
        // 简单 sleep 1 秒，保证线程 A 成功持有锁
        Thread.sleep(1000L);
        //尝试加锁，最多等待 100 秒，上锁以后 10 秒自动解锁
        System.out.printf("准备开始获得锁时间：%s%n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        final RLock lock = redissonClient.getLock(LOCK_KEY);
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if (res) {
            System.out.printf("实际获得锁时间：%s%n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            System.out.println("加锁失败");
        }
    }

}
