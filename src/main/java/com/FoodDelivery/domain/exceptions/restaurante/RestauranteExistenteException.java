package com.FoodDelivery.domain.exceptions.restaurante;

import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class RestauranteExistenteException extends ResourceExistenteException {
    private static final String mensagem = "Restaurante já cadastrado";

    public RestauranteExistenteException() {
        super(mensagem);
    }

}
