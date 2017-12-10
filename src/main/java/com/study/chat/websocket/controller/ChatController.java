package com.study.chat.websocket.controller;

import com.study.chat.websocket.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by rmcodestar on 2017. 11. 29..
 */
@Controller
@Slf4j
public class ChatController {
    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public Message broadcast(Message message) {
        log.info("[message] {}", message);
        return message;
    }
}
