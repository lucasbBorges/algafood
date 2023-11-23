package com.FoodDelivery.api.Controller;

import com.FoodDelivery.api.model.CozinhaDTO;
import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.model.Cozinha;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.service.CozinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @GetMapping
    public List<CozinhaDTO> listarCozinhas() {
        return toModelList(cozinhaService.listar());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{cozinhaId}")
    public CozinhaDTO buscarCozinha(@PathVariable("cozinhaId") Long id) {
        return toModel(cozinhaService.buscar(id));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public CozinhaDTO cadastrarCozinha(@Valid @RequestBody Cozinha cozinha) {
        return toModel(cozinhaService.salvar(cozinha, ResourceEnum.RESOURCE_POST));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{cozinhaid}")
    public CozinhaDTO alterarCozinha(@PathVariable("cozinhaid") Long id
            , @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual;
        cozinhaAtual = cozinhaService.buscar(id);
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return toModel(cozinhaService.salvar(cozinhaAtual, ResourceEnum.RESOURCE_PUT));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{cozinhaid}")
    public void remover(@PathVariable("cozinhaid") Long id) {
        cozinhaService.deletar(id);
    }

    private CozinhaDTO toModel(Cozinha cozinha){
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(cozinha.getId());
        cozinhaDTO.setNome(cozinha.getNome());
        return cozinhaDTO;
    }

    private List<CozinhaDTO> toModelList(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}