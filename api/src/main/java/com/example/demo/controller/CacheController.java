package com.example.demo.controller;

import com.example.demo.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    // ==================== 缓存注解示例 ====================
    
    /**
     * 获取用户信息 - 使用缓存注解
     * 访问: GET /api/cache/user/123
     */
    @GetMapping("/user/{userId}")
    public String getUserInfo(@PathVariable Long userId) {
        return cacheService.getUserInfo(userId);
    }

    /**
     * 更新用户信息 - 自动清除缓存
     * 访问: PUT /api/cache/user/123
     */
    @PutMapping("/user/{userId}")
    public String updateUser(@PathVariable Long userId, @RequestBody String userInfo) {
        cacheService.updateUser(userId, userInfo);
        return "用户信息已更新，缓存已清除";
    }

    // ==================== RedisTemplate示例 ====================
    
    /**
     * 设置缓存
     * 访问: POST /api/cache/set?key=test&value=hello&timeout=60
     */
    @PostMapping("/set")
    public String setCache(@RequestParam String key, 
                          @RequestParam String value, 
                          @RequestParam(defaultValue = "60") long timeout) {
        cacheService.setWithExpire(key, value, timeout, TimeUnit.SECONDS);
        return "缓存设置成功: " + key + " = " + value;
    }

    /**
     * 获取缓存
     * 访问: GET /api/cache/get?key=test
     */
    @GetMapping("/get")
    public Object getCache(@RequestParam String key) {
        return cacheService.get(key);
    }

    /**
     * 删除缓存
     * 访问: DELETE /api/cache/delete?key=test
     */
    @DeleteMapping("/delete")
    public String deleteCache(@RequestParam String key) {
        cacheService.delete(key);
        return "缓存删除成功: " + key;
    }

    /**
     * 分布式锁示例
     * 访问: POST /api/cache/lock?key=order:123&timeout=30
     */
    @PostMapping("/lock")
    public String acquireLock(@RequestParam String key, 
                             @RequestParam(defaultValue = "30") long timeout) {
        boolean acquired = cacheService.setLock(key, "locked", timeout, TimeUnit.SECONDS);
        if (acquired) {
            return "锁获取成功: " + key;
        } else {
            return "锁获取失败，可能已被其他进程占用";
        }
    }

    /**
     * 释放锁
     * 访问: DELETE /api/cache/lock?key=order:123
     */
    @DeleteMapping("/lock")
    public String releaseLock(@RequestParam String key) {
        cacheService.releaseLock(key);
        return "锁释放成功: " + key;
    }

    /**
     * 计数器示例
     * 访问: POST /api/cache/counter?key=visits
     */
    @PostMapping("/counter")
    public String incrementCounter(@RequestParam String key) {
        Long count = cacheService.increment(key);
        return "计数器 " + key + " 当前值: " + count;
    }

    /**
     * 列表操作示例
     * 访问: POST /api/cache/list?key=messages&value=hello
     */
    @PostMapping("/list")
    public String addToList(@RequestParam String key, @RequestParam String value) {
        cacheService.addToList(key, value);
        return "消息已添加到列表: " + key;
    }

    /**
     * 从列表获取消息
     * 访问: GET /api/cache/list?key=messages
     */
    @GetMapping("/list")
    public Object getFromList(@RequestParam String key) {
        return cacheService.getFromList(key);
    }
}
