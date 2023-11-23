package com.FoodDelivery.domain.exceptions.estado;

import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class EstadoExistenteException extends ResourceExistenteException {
    public static final String mensagem = "Estado já cadastrado";

    public EstadoExistenteException() {
        super(mensagem);
    }
}
