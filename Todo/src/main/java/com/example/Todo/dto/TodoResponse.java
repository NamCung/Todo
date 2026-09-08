package com.example.Todo.dto;

public record TodoResponse(
        Long id,
        String title,
        String description,
        boolean completed
) {
}
