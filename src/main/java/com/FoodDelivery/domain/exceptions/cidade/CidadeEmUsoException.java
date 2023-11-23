package com.FoodDelivery.domain.exceptions.cidade;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;

public class CidadeEmUsoException extends ResourceEmUsoException {
    private static final String mensagem = "Cidade já em uso";

    public CidadeEmUsoException() {
        super(mensagem);
    }

}
