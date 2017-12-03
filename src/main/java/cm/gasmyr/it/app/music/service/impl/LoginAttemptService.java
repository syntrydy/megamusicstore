package cm.gasmyr.it.app.music.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {
 
    private final int MAX_ATTEMPT = 5;
    private LoadingCache<String, Integer> attemptsCache;
 
    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
          expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
 
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }
 
    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }
 
    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
