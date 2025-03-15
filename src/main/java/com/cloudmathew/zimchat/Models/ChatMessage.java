package com.cloudmathew.zimchat.Models;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {

    @JsonTypeId
    public Long id;

    private String sender;
    private String content;

}
