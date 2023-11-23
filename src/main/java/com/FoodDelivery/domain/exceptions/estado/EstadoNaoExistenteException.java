package com.FoodDelivery.domain.exceptions.estado;

import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class EstadoNaoExistenteException extends ResourceNaoExistenteException {
    public static final String mensagem = "Estado não existente";

    public EstadoNaoExistenteException () {
        super(mensagem);
    }
}
