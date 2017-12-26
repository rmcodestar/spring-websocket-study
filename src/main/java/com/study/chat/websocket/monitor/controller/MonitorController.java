package com.study.chat.websocket.monitor.controller;

import com.study.chat.websocket.event.LoginEvent;
import com.study.chat.websocket.room.repository.RoomRepository;
import com.study.chat.websocket.user.repository.SessionRepository;
import com.study.chat.websocket.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by rmcodestar on 2017. 12. 13..
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/users")
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
