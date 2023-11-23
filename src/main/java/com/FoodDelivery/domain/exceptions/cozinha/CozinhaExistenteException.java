package com.FoodDelivery.domain.exceptions.cozinha;

import com.FoodDelivery.domain.exceptions.ResourceExistenteException;

public class CozinhaExistenteException extends ResourceExistenteException {
    private static final String mensagem = "Cozinha já cadastrada";

    public CozinhaExistenteException() {
        super(mensagem);
    }
}
