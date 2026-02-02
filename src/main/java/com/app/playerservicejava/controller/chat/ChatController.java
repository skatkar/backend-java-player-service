package com.app.playerservicejava.controller.chat;

import com.app.playerservicejava.model.ChatDetails;
import com.app.playerservicejava.service.chat.ChatClientService;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.Model;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "v1/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatClientService chatClientService;

    @PostMapping
    public ResponseEntity<String> chatBigText(@Valid @RequestBody ChatDetails chatDetails) throws OllamaBaseException, IOException, InterruptedException {
        String response = chatClientService.chatBigText(chatDetails);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{prompt}")
    public String chat(@NotNull @PathVariable String prompt) throws OllamaBaseException, IOException, InterruptedException {
        return chatClientService.chat(prompt);
    }

    @GetMapping("/list-models")
    public ResponseEntity<List<Model>> listModels() throws OllamaBaseException, IOException, URISyntaxException, InterruptedException {
        List<Model> models = chatClientService.listModels();
        return ResponseEntity.ok(models);
    }
}
