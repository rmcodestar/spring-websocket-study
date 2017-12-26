package com.study.chat.websocket.room.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@Data
public class RoomParticipantCollection {
    private String roomId;

    @JsonProperty("collection")
    private List<RoomParticipant> roomParticipants;
}
