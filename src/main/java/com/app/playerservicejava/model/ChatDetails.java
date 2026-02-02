package com.app.playerservicejava.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatDetails {
    @NotBlank(message = "Prompt cannot be empty or whitespace")
    @Size(min = 1, max = 10000, message = "Prompt must be between 1 and 10000 characters")
    private String prompt;

    @NotBlank(message = "Model cannot be empty or whitespace")
    @Size(min = 1, max = 100, message = "Model name must be between 1 and 100 characters")
    private String model;

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
