package com.cpny.common.lock;

/**
 * 分布式锁
 */
public interface DistributedLock {


    /**
     * 分布式锁, 默认3S超时, 等待3S
     * @param lock key值
     * @return 是否获取到
     */
    boolean lock(String lock);

    /**
     * 分布式锁, 指定超时时间, 单位:毫秒
     * @param lock key值
     * @return 是否获取到
     */
    boolean lock(String lock, int lockTime);

    /**
     * 分布式锁, 指定超时时间, 指定等待时间, 单位:毫秒
     * @param lock key值
     * @return 是否获取到
     */
    boolean lock(String lock, int lockTime, int waitTime);

    /**
     * 释放锁
     */
    void unLock();

}
