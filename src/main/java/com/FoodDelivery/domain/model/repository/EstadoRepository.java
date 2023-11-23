package com.FoodDelivery.domain.model.repository;

import com.FoodDelivery.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    List<Estado> findByNome(String nome);
}
