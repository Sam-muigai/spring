package com.sam.spring.service;

import com.sam.spring.exception.TodoCollectionException;
import com.sam.spring.model.TodoDTO;
import com.sam.spring.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl  implements TodoService{

    private final TodoRepository todoRepo;


    @Autowired
    public TodoServiceImpl(TodoRepository todoRepo){
        this.todoRepo = todoRepo;
    }

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException,TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional = todoRepo.findByTodo(todo.getTodo());
        if (todoDTOOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExist());
        }else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0){
            return todos;
        }else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
        if (optionalTodo.isEmpty()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }else {
            return optionalTodo.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoWithId = todoRepo.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());

        if (todoWithId.isPresent()){
            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExist());
            }
            TodoDTO todoToUpdate = todoWithId.get();
            todoToUpdate.setTodo(todo.getTodo() != null? todo.getTodo(): todoToUpdate.getTodo() );
            todoToUpdate.setDescription(todo.getDescription() != null ? todo.getDescription() :todoToUpdate.getDescription());
            todoToUpdate.setCompleted(todo.getCompleted() != null ? todo.getCompleted() :todoToUpdate.getCompleted());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToUpdate);
        }else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteById(String id) throws TodoCollectionException{
        Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
        if (optionalTodo.isPresent()){
            todoRepo.deleteById(id);
        }else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

}
