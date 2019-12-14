package com.example.demofirebaseclient.demofirebaseclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    private String sender;

    private String recipient;

    private List<Message> messages = new ArrayList<>();

}
