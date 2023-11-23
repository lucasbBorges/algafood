package com.FoodDelivery.domain.model.repository;

import com.FoodDelivery.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryInterface {
    List<Restaurante> find (String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}
