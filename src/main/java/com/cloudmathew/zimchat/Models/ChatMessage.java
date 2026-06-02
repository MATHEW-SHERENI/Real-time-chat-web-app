package com.cloudmathew.zimchat.Models;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessage {

    @JsonTypeId
    public Long id;

    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private String fileData; // base64 encoded file data
    private String fileName; // original file name

}
