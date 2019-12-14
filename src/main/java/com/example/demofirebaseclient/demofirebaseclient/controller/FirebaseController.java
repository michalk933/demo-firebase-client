package com.example.demofirebaseclient.demofirebaseclient.controller;

import com.example.demofirebaseclient.demofirebaseclient.dto.Chat;
import com.example.demofirebaseclient.demofirebaseclient.dto.Message;
import com.example.demofirebaseclient.demofirebaseclient.service.FirebaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class FirebaseController {

    private final FirebaseService firebaseService;

    @PostMapping("/create/char")
    public void saveChat(@RequestBody Chat chat) {
        firebaseService.saveChat(chat);
    }

    @PostMapping("/add/message")
    public void saveMessage(
            @RequestParam("chatId") String chatId,
            @RequestBody Message message){
        firebaseService.saveMessage(chatId, message);
    }

}
