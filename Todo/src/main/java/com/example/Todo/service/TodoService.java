package com.example.Todo.service;

import com.example.Todo.dto.TodoRequest;
import com.example.Todo.dto.TodoResponse;
import com.example.Todo.model.Todo;
import com.example.Todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<TodoResponse> getAllTodos(){
        return todoRepository.findAll()
                .stream()
                .map(todo -> new TodoResponse(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getDescription(),
                        todo.isCompleted()
                )).toList();
    }

    private Todo findById(Long id){
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ID existiert nicht: " +id));

        return existingTodo;
    }

    public TodoResponse getTodoById(Long id){
        Todo todo =  todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ID existiert nicht: " +id));

            return new TodoResponse(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getDescription(),
                    todo.isCompleted()
            );
    }

    public TodoResponse updateTodo( Long id, TodoRequest request){
        Todo existingTodo = findById(id);
        existingTodo.setDescription(request.description());
        existingTodo.setTitle(request.title());
        Todo saved = todoRepository.save(existingTodo);

        return new TodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted()
        );
    }

    public TodoResponse createTodoItem(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setCompleted(false);

        Todo saved = todoRepository.save(todo);
        return new TodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted()
        );
    }

    public void deleteTodoItem(Long id){
        if(todoRepository.existsById(id)){
            todoRepository.deleteById(id);
        }
            else {
                throw new NoSuchElementException("ID nicht gefunden: "+id);
            }
        }

        public TodoResponse markCompleted(Long id){
        Todo todo = findById(id);
        todo.setCompleted(!todo.isCompleted());

        Todo saved = todoRepository.save(todo);
        return new TodoResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.isCompleted()
        );
        }

    }


