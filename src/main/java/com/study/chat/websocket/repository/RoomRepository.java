package com.study.chat.websocket.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rmcodestar on 2017. 12. 12..
 */
@Repository
public class RoomRepository {
    @Getter
    private Map<String, Set<String>> rooms = new ConcurrentHashMap<>();

    public void join(String roomId, String userId) {
        Set<String> participants = this.rooms.get(roomId);
        if(CollectionUtils.isEmpty(participants)) {
            participants = new HashSet<>();
        }

        participants.add(userId);
        this.rooms.put(roomId, participants);
    }

    public void leave(String roomId, String userId) {
        Set<String> participants = this.rooms.get(roomId);
        if(CollectionUtils.isEmpty(participants)) {
            return;
        }

        participants.remove(userId);
    }

    public Set<String> getRoomParticipants(String roomId){
        return this.rooms.get(roomId);
    }
}
