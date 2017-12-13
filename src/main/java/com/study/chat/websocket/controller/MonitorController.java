package com.study.chat.websocket.controller;

import com.study.chat.websocket.event.LoginEvent;
import com.study.chat.websocket.repository.RoomRepository;
import com.study.chat.websocket.repository.SessionRepository;
import com.study.chat.websocket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@RequestMapping("/monitor")
@Controller
public class MonitorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @ResponseBody
    @RequestMapping("/users")
    public Map<String, String>  getAllUser() {
        return userRepository.getUsers();
    }

    @ResponseBody
    @RequestMapping("/sessions")
    public Collection<LoginEvent> getSessions() {
        return sessionRepository.getActiveSessions().values();
    }

    @ResponseBody
    @RequestMapping("/rooms")
    public Map<String, Set<String>> getRooms() {
        return roomRepository.getRooms();
    }
}
