package com.sam.spring.exception;

import java.io.Serial;

public class TodoCollectionException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public TodoCollectionException(String message){
        super(message);
    }

    public static String NotFoundException(String id){
        return "Todo with id "+id+"not found!";
    }


    public static String TodoAlreadyExist(){
        return "Todo Already exists";
    }


}
