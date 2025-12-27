package com.example.template.app.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGreetingRequest(
    @NotBlank @Size(max = 100) String name
) { }
