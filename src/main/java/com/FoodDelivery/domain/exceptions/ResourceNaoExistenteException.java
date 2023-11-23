package com.FoodDelivery.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNaoExistenteException extends RuntimeException{
    public ResourceNaoExistenteException(String mensagem){
        super(mensagem);
    }
}
