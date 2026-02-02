package com.app.playerservicejava.service.chat;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.Model;
import io.github.ollama4j.models.OllamaResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatClientServiceTest {

    @InjectMocks
    ChatClientService chatClientService;

    @Mock
    OllamaAPI mockOllamaApi;

    @Test
    @DisplayName("Test to verify list models method")
    void test_listModels() throws Exception {
        Model model1 = mock(Model.class);
        Model model2 = mock(Model.class);

        List<Model> models = new ArrayList<>();
        models.add(model1);
        models.add(model2);

        when(mockOllamaApi.listModels()).thenReturn(models);

        List<Model> actual = chatClientService.listModels();

        assertThat(actual).isSameAs(models);

        verify(mockOllamaApi, times(1)).listModels();
    }

    @Test
    @DisplayName("Test to verify the chat functionality")
    void test_chat() throws Exception{
        OllamaResult response = mock(OllamaResult.class);

        when(response.getResponse()).thenReturn("Mocked chat response");
        when(mockOllamaApi.generate(anyString(), anyString(), anyBoolean(), any())).thenReturn(response);

        String chatText = chatClientService.chat();

        assertNotNull(chatText);
        verify(mockOllamaApi, times(1)).generate(anyString(), anyString(), anyBoolean(), any());
    }


}
