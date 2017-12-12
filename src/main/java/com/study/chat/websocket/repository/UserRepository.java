package com.study.chat.websocket.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Repository
public class UserRepository {
    @Getter
    private Map<String, String> users = new ConcurrentHashMap<>();

    @Getter
    private Map<String, String> sessionPool = new ConcurrentHashMap<>();

    public void save(String userId, String sessionId) {
        users.put(userId, sessionId);
        sessionPool.put(sessionId, userId);
    }

    public String getSessionId(String userId) {
        return this.users.get(userId);
    }

    public Set<String> getAllUserId() {
        return this.users.keySet();
    }

    public void remove(String sessionId) {
        sessionPool.remove(sessionId);
    }
}
