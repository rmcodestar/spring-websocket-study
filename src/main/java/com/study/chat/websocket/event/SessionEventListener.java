package com.study.chat.websocket.event;

import com.study.chat.websocket.user.repository.SessionRepository;
import com.study.chat.websocket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Component
public class SessionEventListener {
    private static final String HEADER_USER_ID = "userId";

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String userId = headers.getNativeHeader(HEADER_USER_ID).stream()
                .limit(1)
                .map(String::toString)
                .collect(Collectors.joining());

        LoginEvent loginEvent = new LoginEvent()
                .setSessionId(headers.getSessionId())
                .setTime(new Date());

        userRepository.save(userId, headers.getSessionId());
        sessionRepository.add(headers.getSessionId(), loginEvent);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        Optional.ofNullable(sessionRepository.getParticipant(event.getSessionId()))
                .ifPresent(login -> sessionRepository.removeParticipant(event.getSessionId()));

        userRepository.remove(event.getSessionId());
    }
}
