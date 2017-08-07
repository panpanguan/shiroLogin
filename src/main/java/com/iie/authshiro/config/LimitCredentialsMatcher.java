package com.iie.authshiro.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cyberpecker on 2017/4/12.
 */
public class LimitCredentialsMatcher extends HashedCredentialsMatcher {
    //集群中可能会导致出现验证多过3次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;

    private int limitCount = 3;

    public LimitCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passWordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        Collection<AtomicInteger> time = passwordRetryCache.values();
        if(retryCount.incrementAndGet() > limitCount) {
            //返回冻结账户名
            throw new ExcessiveAttemptsException(username+"被冻结了20秒");
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (!matches){
            //返回剩余次数
            int leave = limitCount - retryCount.intValue()+1;
            throw new IncorrectCredentialsException(String.valueOf(leave));
        }
        if(matches) {
            //clear retry data
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
