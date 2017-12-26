package com.study.chat.websocket.room.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@Data
@Accessors(chain = true)
public class RoomParticipant {
    private String roomId;
    private String userId;
    private boolean presented;
}
