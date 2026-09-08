package com.example.Todo.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoRequest (
    @NotBlank(message = "Titel darf nicht leer sein")
    String title,
    String description
){}
