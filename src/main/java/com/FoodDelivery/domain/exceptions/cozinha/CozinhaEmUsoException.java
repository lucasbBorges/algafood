package com.FoodDelivery.domain.exceptions.cozinha;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;

public class CozinhaEmUsoException extends ResourceEmUsoException {
    private static final String mensagem = "Cozinha em uso";

    public CozinhaEmUsoException() {
        super(mensagem);
    }
}
