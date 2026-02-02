package com.app.playerservicejava.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class ChatDetails {
    @NotEmpty
    @NotBlank
    String prompt;

    @NotEmpty
    @NotBlank
    String model;

    public ChatDetails() {
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
