package com.FoodDelivery.domain.exceptions.cozinha;

import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class CozinhaNaoExistenteException extends ResourceNaoExistenteException {
    private static final String mensagem = "Cozinha não existente";

    public CozinhaNaoExistenteException () {
        super(mensagem);
    }
}
