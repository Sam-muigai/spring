package com.sam.spring.controller;

import com.sam.spring.exception.TodoCollectionException;
import com.sam.spring.model.TodoDTO;
import com.sam.spring.repository.TodoRepository;
import com.sam.spring.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {


    private final TodoService todoService;


    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try{
            return new ResponseEntity<>(todoService.getSingleTodo(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateATodo(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        try{
            todoService.updateTodo(id,todo);
            return new ResponseEntity<>("Updated todo with id " + id,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteATodo(@PathVariable("id") String id) {
        try {
           todoService.deleteById(id);
           return new ResponseEntity<>("Todo with id "+id+" deleted",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Todo Item not found", HttpStatus.NOT_FOUND);
        }
    }
}
