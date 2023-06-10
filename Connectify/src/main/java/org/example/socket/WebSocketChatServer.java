package org.example.socket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;


@ServerEndpoint("/chat/{room}")
public class WebSocketChatServer {
    private static Map<String, Set<Session>> rooms = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        rooms.computeIfAbsent(room, key -> Collections.synchronizedSet(new HashSet<>())).add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("room") String room) throws IOException {
        Set<Session> participants = rooms.get(room);
        if (participants != null) {
            synchronized (participants) {
                for (Session participant : participants) {
                    if (participant.isOpen() && !participant.equals(session)) {
                        participant.getBasicRemote().sendText(message);
                    }
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String room) {
        Set<Session> participants = rooms.get(room);
        if (participants != null) {
            participants.remove(session);
        }
    }
}