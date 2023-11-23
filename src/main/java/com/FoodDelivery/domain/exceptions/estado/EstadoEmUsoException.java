package com.FoodDelivery.domain.exceptions.estado;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class EstadoEmUsoException extends ResourceEmUsoException {
    public static final String mensagem = "Estado já em uso";

    public EstadoEmUsoException() {
        super(mensagem);
    }
}
