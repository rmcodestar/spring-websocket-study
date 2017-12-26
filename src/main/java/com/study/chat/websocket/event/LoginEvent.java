package com.study.chat.websocket.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Data
@Accessors(chain = true)
public class LoginEvent {
    private String sessionId;
    private String userId;
    private Date time;
}
