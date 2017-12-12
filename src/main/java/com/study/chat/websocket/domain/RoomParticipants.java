package com.study.chat.websocket.domain;

import lombok.Data;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@Data
public class RoomParticipants {
    private String roomId;
    private String userId;
    private boolean presented;
}
