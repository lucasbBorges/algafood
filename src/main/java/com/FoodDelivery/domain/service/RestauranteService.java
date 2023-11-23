package com.FoodDelivery.domain.service;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.exceptions.restaurante.RestauranteEmUsoException;
import com.FoodDelivery.domain.exceptions.restaurante.RestauranteExistenteException;
import com.FoodDelivery.domain.exceptions.restaurante.RestauranteNaoExistenteException;
import com.FoodDelivery.domain.exceptions.restaurante.TaxaFreteInvalidaException;
import com.FoodDelivery.domain.model.Cozinha;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.model.Restaurante;
import com.FoodDelivery.domain.model.repository.CozinhaRepository;
import com.FoodDelivery.domain.model.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaService cozinhaService;

    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    public List<Restaurante> buscarComParametros (String nome,
                                                  BigDecimal taxaInicial,
                                                  BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }
    public Restaurante salvar(Restaurante restaurante, ResourceEnum ENUM){
        Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());
        if (!restauranteRepository.findByNome(restaurante.getNome()).isEmpty()
                && ENUM == ResourceEnum.RESOURCE_POST){
            throw new RestauranteExistenteException();
        }
        if (!restaurante.getTaxaFrete().remainder(BigDecimal.valueOf(5)).equals(BigDecimal.ZERO)){
            throw new TaxaFreteInvalidaException("Valor deve ser um múltiplo de 5");
        }
        return restauranteRepository.save(restaurante);
    }
    public Restaurante buscar(Long id){
        return restauranteRepository.findById(id)
                .orElseThrow(RestauranteNaoExistenteException::new);
    }

    public void deletar(Long id){
        try{
            Restaurante restaurante = buscar(id);
            restauranteRepository.delete(restaurante);
        }catch (DataIntegrityViolationException e){
            throw new RestauranteEmUsoException();
        }
    }
}
