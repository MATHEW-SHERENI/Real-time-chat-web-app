package com.cloudmathew.zimchat.Controller;

import com.cloudmathew.zimchat.Models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @MessageMapping("/sendMessages")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message){
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String chat(){
        return "chat";
    }
}
