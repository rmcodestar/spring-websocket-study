package com.study.chat.websocket.message.domain;

import lombok.Data;

/**
 * Created by rmcodestar on 2017. 11. 29..
 */
@Data
public class ChattingMessage {
    private String roomId;
    private String userId;
    private String message;
}
