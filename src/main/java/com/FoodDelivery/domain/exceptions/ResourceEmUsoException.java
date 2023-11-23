package com.FoodDelivery.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceEmUsoException extends RuntimeException{
    public ResourceEmUsoException(String mensagem){
        super(mensagem);
    }
}
