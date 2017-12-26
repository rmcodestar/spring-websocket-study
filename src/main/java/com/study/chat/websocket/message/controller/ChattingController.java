package com.study.chat.websocket.message.controller;

import com.study.chat.websocket.message.domain.ChattingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by rmcodestar on 2017. 11. 29..
 */
@Slf4j
@Controller
public class ChattingController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * client (/app/message) --> server
     *
     * @param message chatting message
     * @See ChatConfiguration.configureMessageBroker ApplicationDestinationPrefixes
     */
    @MessageMapping("/message")
    public void receiveMessage(@Payload ChattingMessage message) {
        log.info("[receive message] {}", message);

        simpMessagingTemplate.convertAndSend("/chat/messages." + message.getRoomId(), message);
    }

    /**
     * server --> client (event driven)
     * client subscribe channel (/chat/messages.{roomId})
     *
     * @param message chatting message in same room
     * @param roomId  room id
     * @return
     */
    @SubscribeMapping("/chat/messages.{roomId}")
    public ChattingMessage chatInRoom(@Payload ChattingMessage message, @DestinationVariable String roomId) {
        log.info("[send message in room] roomId : {}, {}", roomId, message);

        return message;
    }
}