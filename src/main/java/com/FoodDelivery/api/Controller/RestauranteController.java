package com.FoodDelivery.api.Controller;

import com.FoodDelivery.api.model.CozinhaDTO;
import com.FoodDelivery.api.model.RestauranteDTO;
import com.FoodDelivery.domain.exceptions.NegocioException;
import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.exceptions.cidade.CidadeNaoExistenteException;
import com.FoodDelivery.domain.exceptions.cozinha.CozinhaNaoExistenteException;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.model.Restaurante;
import com.FoodDelivery.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    RestauranteService restauranteService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<RestauranteDTO> listar(){
        return toModelList(restauranteService.listar());
    }

    @GetMapping (value = "/{restauranteId}")
    public RestauranteDTO buscarRestaurante(@PathVariable("restauranteId") Long id){
        return toModel(restauranteService.buscar(id));
    }

    @PostMapping
    public RestauranteDTO cadastrarRestaurante(@Valid @RequestBody Restaurante restaurante){
        try{
            return toModel(restauranteService.salvar(restaurante, ResourceEnum.RESOURCE_POST));
        } catch (CozinhaNaoExistenteException e){
            throw new NegocioException("Cozinha não cadastrada");
        }
    }

    @PutMapping (value = "/{restauranteId}")
    public RestauranteDTO alterarCadastroRestaurante (@PathVariable("restauranteId") Long id,
                                                         @RequestBody Restaurante restaurante){
        Restaurante restauranteAtual;
        restauranteAtual = restauranteService.buscar(id);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id"
                , "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return toModel(restauranteService.salvar(restauranteAtual, ResourceEnum.RESOURCE_PUT));
        } catch (CidadeNaoExistenteException e) {
            throw new NegocioException("Cidade não encontrada");
        }
    }

    @DeleteMapping (value = "/{restauranteId}")
    public void deletarRestaurante(@PathVariable("restauranteId") Long id){
        restauranteService.deletar(id);
    }

    private RestauranteDTO toModel(Restaurante restaurante){
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);

        return restauranteDTO;
    }

    private List<RestauranteDTO> toModelList(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
