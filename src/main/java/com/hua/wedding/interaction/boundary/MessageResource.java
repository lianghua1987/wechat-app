package com.hua.wedding.interaction.boundary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class MessageResource {

    private static Map<String, String> messageMap = new ConcurrentHashMap<>(1000);

    @GetMapping
    public String getAll() {
        return String.format("Hello Hua");
    }


    @PostMapping
    public Map<String, String> addMessage(@RequestParam(value = "message", defaultValue = "") String message) {
        messageMap.put(UUID.randomUUID().toString(), message);
        return messageMap;
    }
}
