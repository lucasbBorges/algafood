package com.FoodDelivery.domain.exceptions.restaurante;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class RestauranteEmUsoException extends ResourceEmUsoException {
    private static final String mensagem = "Restaurante em uso";

    public RestauranteEmUsoException() {
        super(mensagem);
    }

}
