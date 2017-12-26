package com.study.chat.websocket.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Data
@AllArgsConstructor
public class User {
    private String userId;
    private String sessionId;
}
