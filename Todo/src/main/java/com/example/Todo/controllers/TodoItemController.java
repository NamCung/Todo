package com.example.Todo.controllers;

import com.example.Todo.dto.TodoRequest;
import com.example.Todo.dto.TodoResponse;
import com.example.Todo.model.Todo;
import com.example.Todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoItemController {
    private  final Logger log = LoggerFactory.getLogger(TodoItemController.class);
    private final TodoService todoService;
    //@Autowired TodoService todoService;
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodoItems() {return ResponseEntity.ok(todoService.getAllTodos());}

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id){
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodoItem(@RequestBody @Valid TodoRequest request){
        TodoResponse createdTodo = todoService.createTodoItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping ("/{id}")
    public ResponseEntity <TodoResponse> updateTodo (
            @PathVariable Long id,
            @RequestBody @Valid TodoRequest request
    ){
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponse> markCompleted(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.markCompleted(id));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <Void> deleteTodoItem(@PathVariable Long id){
        todoService.deleteTodoItem(id);
        return ResponseEntity.noContent().build();
    }
}
