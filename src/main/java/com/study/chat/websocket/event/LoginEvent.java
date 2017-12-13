package com.study.chat.websocket.event;

import lombok.Data;

import java.util.Date;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Data
public class LoginEvent {
    private String sessionId;
    private Date time;
}
