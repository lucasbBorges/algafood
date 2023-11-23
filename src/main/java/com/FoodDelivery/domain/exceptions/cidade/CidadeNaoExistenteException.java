package com.FoodDelivery.domain.exceptions.cidade;

import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;

public class CidadeNaoExistenteException extends ResourceNaoExistenteException {
    private static final String mensagem = "Cidade não existente";

    public CidadeNaoExistenteException () {
        super(mensagem);
    }

}
