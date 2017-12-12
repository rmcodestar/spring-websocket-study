package com.study.chat.websocket.repository;

import com.study.chat.websocket.event.LoginEvent;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Repository
public class SessionRepository {
    @Getter
    private Map<String, LoginEvent> activeSessions = new ConcurrentHashMap<>();

    public void add(String sessionId, LoginEvent event) {
        activeSessions.put(sessionId, event);
    }

    public LoginEvent getParticipant(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void removeParticipant(String sessionId) {
        activeSessions.remove(sessionId);
    }
}
