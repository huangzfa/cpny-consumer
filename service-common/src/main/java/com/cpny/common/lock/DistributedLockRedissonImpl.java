package com.cpny.common.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁
 */
public class DistributedLockRedissonImpl implements DistributedLock {

    //lock时间
    public static final int LOCK_EXPIRE = 3000; // 3s

    @Autowired
    private RedissonClient redissonClient;

    private ThreadLocal<RLock> lockLocal = new ThreadLocal<>();

    /**
     * 分布式锁
     * @param key
     * @return
     */
    public boolean lock(String key){
        return lock(key, LOCK_EXPIRE, LOCK_EXPIRE);
    }


    /**
     * 分布式锁, 指定超时时间
     * @param key
     * @return
     */
    public boolean lock(String key, int lockTime){
        return lock(key, lockTime, lockTime);
    }

    /**
     * 分布式锁, 指定超时时间, 指定等待时间
     * @param key
     * @return
     */
    public boolean lock(String key, int lockTime, int waitTime){
        try {
            RLock lock = redissonClient.getLock(key);
            boolean isSucc = lock.tryLock(waitTime, lockTime, TimeUnit.MILLISECONDS);
            if(isSucc){
                lockLocal.set(lock);
            }
            return isSucc;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     */
    public void unLock(){
        try {
            RLock lock = lockLocal.get();
            if (lock != null) {
                lock.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}