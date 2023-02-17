package com.sam.spring.service;

import com.sam.spring.exception.TodoCollectionException;
import com.sam.spring.model.TodoDTO;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface TodoService {

    void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    List<TodoDTO> getAllTodos();

    TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    void updateTodo(String id,TodoDTO todo) throws TodoCollectionException;

    void deleteById(String id) throws TodoCollectionException;

}
