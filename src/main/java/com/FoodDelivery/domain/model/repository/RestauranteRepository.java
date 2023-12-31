package com.FoodDelivery.domain.model.repository;

import com.FoodDelivery.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,
        RestauranteRepositoryInterface,
        JpaSpecificationExecutor<Restaurante> {
    List<Restaurante> findByNome (String nome);
}
