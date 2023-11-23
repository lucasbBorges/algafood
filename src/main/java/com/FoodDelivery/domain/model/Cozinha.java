package com.FoodDelivery.domain.model;

import com.FoodDelivery.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name="cozinha")
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotBlank
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    public List<Restaurante> restaurantes;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cozinha cozinha)) return false;
        return Objects.equals(getId(), cozinha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
