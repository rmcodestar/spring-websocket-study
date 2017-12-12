package com.study.chat.websocket.domain;

import lombok.Data;

import java.util.List;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@Data
public class RoomParticipantCollection {
    private String roomId;
    private List<RoomParticipants> collection;
}
