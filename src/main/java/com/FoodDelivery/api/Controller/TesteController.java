package com.FoodDelivery.api.Controller;

import com.FoodDelivery.domain.model.Cozinha;
import com.FoodDelivery.domain.model.Restaurante;
import com.FoodDelivery.domain.model.repository.RestauranteRepository;
import com.FoodDelivery.domain.service.CozinhaService;
import com.FoodDelivery.domain.service.RestauranteService;
import com.FoodDelivery.infra.repository.RestauranteNomeSemelhanteSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TesteController {
    @Autowired
    CozinhaService cozinhaService;

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping(value = "cozinhas/por-nome")
    public ResponseEntity<List<Cozinha>> buscarNome (String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.buscarPorNome(nome));
    }

    @GetMapping(value = "restaurantes/por-nome")
    public ResponseEntity<List<Restaurante>> buscarRestauranteNome (String nome
            , BigDecimal taxaInicial
            , BigDecimal taxaFinal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(restauranteService.buscarComParametros(nome, taxaInicial, taxaFinal));
    }

    @GetMapping(value = "restaurantes/nome-semelhante")
    public List<Restaurante> nomeSemelhante (String nome) {
        var nomeSpec = new RestauranteNomeSemelhanteSpec(nome);
        return restauranteRepository.findAll(nomeSpec);
    }

}
