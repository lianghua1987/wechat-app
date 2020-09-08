package com.hua.wedding.message.boundary;

import com.hua.wedding.message.entity.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat")
public class Resource {

    private static Map<String, Session> messages = new ConcurrentHashMap<>(1000);

//    @GetMapping
//    public String getAll() {
//        return String.format("Hello Hua");
//    }
//
//
//    @PostMapping
//    public Map<String, String> addMessage(@RequestParam(value = "message", defaultValue = "") String message) {
//        messageMap.put(UUID.randomUUID().toString(), message);
//        return messageMap;
//    }

    @OnOpen
    public void onOpen(Session session) {
        messages.put(session.getId(), session);
        //sendMessageToAll(Message.jsonStr(Message.ENTER, "", "", messages.size()));
    }

    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //Message message = JSON.parseObject(jsonStr, Message.class);
        //sendMessageToAll(Message.jsonStr(Message.SPEAK, message.getUsername(), message.getMsg(), messages.size()));
        publish(jsonStr);
    }

    @OnClose
    public void onClose(Session session) {
        messages.remove(session.getId());
        //sendMessageToAll(Message.jsonStr(Message.QUIT, "", "下线了！", messages.size()));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        //error.printStackTrace();
    }

    private static void publish(String msg) {
        messages.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}