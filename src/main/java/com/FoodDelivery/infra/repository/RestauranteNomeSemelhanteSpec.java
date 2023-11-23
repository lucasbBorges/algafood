package com.FoodDelivery.infra.repository;

import com.FoodDelivery.domain.model.Restaurante;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class RestauranteNomeSemelhanteSpec implements Specification<Restaurante> {

    private String nome;

    public RestauranteNomeSemelhanteSpec (String nome) {
        this.nome = nome;
    }
    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(root.get("nome"), "%" + nome + "%");
    }
}
