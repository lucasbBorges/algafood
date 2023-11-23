package com.FoodDelivery.domain.exceptions.cidade;

import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class CidadeExistenteException extends ResourceExistenteException {
    private static final String mensagem = "Cidade já cadastrada";

    public CidadeExistenteException() {
        super(mensagem);
    }

}
