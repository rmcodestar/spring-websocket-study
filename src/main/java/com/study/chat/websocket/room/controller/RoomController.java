package com.study.chat.websocket.room.controller;

import com.study.chat.websocket.message.domain.ChattingMessage;
import com.study.chat.websocket.room.domain.RoomParticipant;
import com.study.chat.websocket.room.domain.RoomParticipantCollection;
import com.study.chat.websocket.room.repository.RoomRepository;
import com.study.chat.websocket.user.repository.SessionRepository;
import com.study.chat.websocket.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Created by rmcodestar on 2017. 12. 19..
 */
@Slf4j
@Controller
public class RoomController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/joinRoom")
    public void joinRoom(@Payload ChattingMessage message) {
        log.info("[join room] {}", message);

        roomRepository.join(message.getRoomId(), message.getUserId());
        simpMessagingTemplate.convertAndSend("/participants/room." + message.getRoomId(), getParticipants(message.getRoomId()));
    }

    @MessageMapping("/leaveRoom")
    public void leaveRoom(@Payload ChattingMessage message) {
        log.info("[leave room] {}", message);

        roomRepository.leave(message.getRoomId(), message.getUserId());
        simpMessagingTemplate.convertAndSend("/participants/room." + message.getRoomId(), getParticipants(message.getRoomId()));
    }

    private RoomParticipantCollection getParticipants(final String roomId) {
        RoomParticipantCollection collection = new RoomParticipantCollection();
        List<RoomParticipant> participants = new ArrayList<>();

        for (String userId : Optional.ofNullable(roomRepository.getRoomParticipants(roomId)).orElse(Collections.emptySet())) {
            RoomParticipant participant = new RoomParticipant();
            participant.setUserId(userId);
            participant.setRoomId(roomId);

            String sessionId = userRepository.getSessionId(userId);
            participant.setPresented(Objects.nonNull(sessionRepository.getParticipant(sessionId)));

            participants.add(participant);
        }

        collection.setRoomId(roomId);
        collection.setRoomParticipants(participants);

        log.info("[roomParticipantCollection] message : {}", collection);

        return collection;
    }

}
