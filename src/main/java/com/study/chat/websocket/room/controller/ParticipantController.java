package com.study.chat.websocket.room.controller;

import com.study.chat.websocket.room.domain.RoomParticipantCollection;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by rmcodestar on 2017. 12. 26..
 */
@Controller
public class ParticipantController {
    @SubscribeMapping("/participants/room.{roomId}")
    public RoomParticipantCollection subscribeParticipants(@Payload RoomParticipantCollection message, @DestinationVariable String roomId) {
        return message;
    }
}
