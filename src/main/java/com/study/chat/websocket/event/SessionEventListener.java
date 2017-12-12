package com.study.chat.websocket.event;

import com.study.chat.websocket.repository.SessionRepository;
import com.study.chat.websocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Component
public class SessionEventListener {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setUserId(headers.getSessionId());

        sessionRepository.add(headers.getSessionId(), loginEvent);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        Optional.ofNullable(sessionRepository.getParticipant(event.getSessionId()))
                .ifPresent(login -> {
                    sessionRepository.removeParticipant(event.getSessionId());
                });

        userRepository.remove(event.getSessionId());
    }
}
