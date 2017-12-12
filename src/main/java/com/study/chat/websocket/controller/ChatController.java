package com.study.chat.websocket.controller;

import com.study.chat.websocket.domain.Message;
import com.study.chat.websocket.domain.RoomParticipants;
import com.study.chat.websocket.repository.RoomRepository;
import com.study.chat.websocket.repository.SessionRepository;
import com.study.chat.websocket.repository.UserRepository;
import com.study.chat.websocket.domain.RoomParticipantCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by rmcodestar on 2017. 11. 29..
 */
@Controller
@Slf4j
public class ChatController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    public void receiveMessage(@Payload Message message) {
        log.info("[receive message] {}", message);
        simpMessagingTemplate.convertAndSend("/chat/messages." + message.getRoomId(), message);
    }

    @MessageMapping("/participants")
    public void requestRoomParticipants(@Payload RoomParticipantCollection message) {
        log.info("[requestRoomParticipants] {}", message);

        message.setCollection(roomRepository.getRoomParticipants(message.getRoomId()).stream()
                .map(userId -> {
                    RoomParticipants participants = new RoomParticipants();
                    participants.setUserId(userId);
                    participants.setRoomId(message.getRoomId());

                    String sessionId = userRepository.getSessionId(userId);
                    participants.setPresented(Objects.nonNull(sessionRepository.getParticipant(sessionId)));

                    return participants;
                })
                .collect(Collectors.toList()));

        simpMessagingTemplate.convertAndSend("/participants/room." + message.getRoomId(), message);
    }

    @SubscribeMapping("/participants/room.{roomId}")
    public RoomParticipantCollection tossRoomParticipants(@Payload RoomParticipantCollection message, @DestinationVariable String roomId) {
        return message;
    }

    @SubscribeMapping("/chat/messages.{roomId}")
    public Message chatInRoom(@Payload Message message, @DestinationVariable String roomId) {
        log.info("[send message in room] {}", message);
        return message;
    }

    @MessageMapping("/joinRoom")
    public void createRoom(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        log.info("[join room] {}", message);

        userRepository.save(message.getUserId(), headerAccessor.getSessionId());
        roomRepository.join(message.getRoomId(), message.getUserId());
    }
}
