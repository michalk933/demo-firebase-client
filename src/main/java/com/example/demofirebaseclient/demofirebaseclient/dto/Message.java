package com.example.demofirebaseclient.demofirebaseclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String author;

    private String msg;

    private long create = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();

}
