package com.app.playerservicejava.controller.chat;

import com.app.playerservicejava.model.ChatDetails;
import com.app.playerservicejava.service.chat.ChatClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ollama4j.models.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatClientService chatClientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test POST /v1/chat/{prompt} - should return chat response for valid prompt")
    void testChatWithPrompt() throws Exception {
        // Given
        String prompt = "Hello, how are you?";
        String expectedResponse = "I'm doing well, thank you!";

        when(chatClientService.chat(prompt)).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(post("/v1/chat/{prompt}", prompt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(chatClientService, times(1)).chat(prompt);
    }

    @Test
    @DisplayName("Test POST /v1/chat - should return chat response for ChatDetails")
    void testChatBigText() throws Exception {
        // Given
        ChatDetails chatDetails = new ChatDetails();
        chatDetails.setPrompt("Tell me about Spring Boot");
        chatDetails.setModel("llama2");

        String expectedResponse = "Spring Boot is a framework...";

        when(chatClientService.chatBigText(any(ChatDetails.class))).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(post("/v1/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chatDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(chatClientService, times(1)).chatBigText(any(ChatDetails.class));
    }

    @Test
    @DisplayName("Test GET /v1/chat/list-models - should return list of models")
    void testListModels() throws Exception {
        // Given
        List<Model> models = new ArrayList<>();
        // Create mock models since the real Model class structure might be complex
        Model model1 = mock(Model.class);
        Model model2 = mock(Model.class);
        models.add(model1);
        models.add(model2);

        when(chatClientService.listModels()).thenReturn(models);

        // When & Then
        mockMvc.perform(get("/v1/chat/list-models")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(chatClientService, times(1)).listModels();
    }

    @Test
    @DisplayName("Test POST /v1/chat/{prompt} - should handle empty prompt")
    void testChatWithEmptyPrompt() throws Exception {
        // Given
        String prompt = "";
        String expectedResponse = "Please provide a valid prompt";

        when(chatClientService.chat(prompt)).thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(post("/v1/chat/" + prompt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(chatClientService, times(1)).chat(prompt);
    }
}

