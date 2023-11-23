package com.FoodDelivery.domain.exceptions.restaurante;

import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class RestauranteNaoExistenteException extends ResourceNaoExistenteException {
    private static final String mensagem = "Restaurante não encontrado";

    public RestauranteNaoExistenteException() {
        super(mensagem);
    }

}
