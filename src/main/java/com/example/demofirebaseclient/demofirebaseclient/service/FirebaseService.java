package com.example.demofirebaseclient.demofirebaseclient.service;

import com.example.demofirebaseclient.demofirebaseclient.dto.Chat;
import com.example.demofirebaseclient.demofirebaseclient.dto.Message;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService {

    @Value("${server.urlSaveChat}")
    private String urlSaveChat;

    @Value("${server.urlSaveMessage}")
    private String urlSaveMessage;

    private final static String CHAT_ID = "chatId";

    private final RestTemplate restTemplate;

    public void saveChat(final Chat chat) {
        restTemplate.postForObject(urlSaveChat, chat, Void.class);
    }

    public void saveMessage(final String chatId, final Message message) {
        Map<String, String> params = new HashMap<>();
        params.put(CHAT_ID, chatId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlSaveMessage);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        restTemplate.postForObject(builder.toUriString(), message, Void.class);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void get() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("/Users/michalkuchciak/IdeaProjects/demo-firebase-client/src/main/resources/livedemo-5125e-firebase-adminsdk-6cfka-e157f86144.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://livedemo-5125e.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("chat").document("5df54b313d88382cfdf2df9a");
        docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                System.err.println("Listen failed: " + e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                log.info("Current data: " + snapshot.getData());
            } else {
                log.info("Current data: null");
            }
        });

    }

}
