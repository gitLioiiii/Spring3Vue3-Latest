package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ==================== 缓存注解方式 ====================
    
    /**
     * 查询用户信息 - 使用缓存注解
     * 适合：简单的查询缓存
     */
    @Cacheable(value = "users", key = "#userId")
    public String getUserInfo(Long userId) {
        System.out.println("从数据库查询用户信息: " + userId);
        // 模拟数据库查询
        return "用户信息: " + userId;
    }

    /**
     * 更新用户信息 - 清除缓存
     * 适合：数据更新后自动清除相关缓存
     */
    @CacheEvict(value = "users", key = "#userId")
    public void updateUser(Long userId, String userInfo) {
        System.out.println("更新用户信息: " + userId);
        // 更新数据库
    }

    /**
     * 更新并缓存用户信息
     * 适合：更新后需要立即缓存新数据
     */
    @CachePut(value = "users", key = "#userId")
    public String updateAndCacheUser(Long userId, String userInfo) {
        System.out.println("更新并缓存用户信息: " + userId);
        return userInfo;
    }

    // ==================== RedisTemplate方式 ====================
    
    /**
     * 设置带过期时间的缓存
     * 适合：需要精确控制过期时间的场景
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置分布式锁
     * 适合：防止重复操作、分布式协调
     */
    public boolean setLock(String key, String value, long timeout, TimeUnit unit) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return result != null && result;
    }

    /**
     * 释放分布式锁
     */
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 计数器操作
     * 适合：访问计数、点赞数等
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 列表操作 - 添加元素
     * 适合：消息队列、最新记录等
     */
    public void addToList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 列表操作 - 获取元素
     */
    public Object getFromList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 哈希操作 - 设置字段
     * 适合：对象属性缓存
     */
    public void setHashField(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 哈希操作 - 获取字段
     */
    public Object getHashField(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 集合操作 - 添加成员
     * 适合：标签、分类等
     */
    public void addToSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 集合操作 - 检查成员
     */
    public boolean isMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }
}
