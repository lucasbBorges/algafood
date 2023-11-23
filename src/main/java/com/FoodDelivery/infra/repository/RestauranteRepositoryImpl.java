package com.FoodDelivery.infra.repository;

import com.FoodDelivery.domain.model.Restaurante;
import com.FoodDelivery.domain.model.repository.RestauranteRepositoryInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryInterface {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        StringBuffer jpql = new StringBuffer();
        Map<String, Object> parametros = new HashMap<>();

        jpql.append("from Restaurante where 0 = 0 ");

        if (StringUtils.hasLength(nome)){
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }
        if (taxaInicial != null) {
            jpql.append("and taxaFrete > :taxaInicial ");
            parametros.put("taxaInicial", taxaInicial);
        }

        if (taxaFinal != null) {
            jpql.append("and taxaFrete < :taxaFinal");
            parametros.put("taxaFinal", taxaFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach(query::setParameter);

        return query.getResultList();
    }
}
