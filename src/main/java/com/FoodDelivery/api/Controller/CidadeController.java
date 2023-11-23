package com.FoodDelivery.api.Controller;

import com.FoodDelivery.domain.exceptions.NegocioException;
import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.exceptions.estado.EstadoNaoExistenteException;
import com.FoodDelivery.domain.model.Cidade;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CidadeService cidadeService;

    @ResponseStatus (HttpStatus.OK)
    @GetMapping
    public List<Cidade> listarCidades(){
        return cidadeService.listar();
    }

    @ResponseStatus (HttpStatus.OK)
    @GetMapping(value = "/{cidadeId}")
    public Cidade buscarCidade(@PathVariable("cidadeId") Long id){
        return cidadeService.buscar(id);
    }

    @ResponseStatus (HttpStatus.CREATED)
    @PostMapping
    public Cidade cadastrarCidade(@Valid @RequestBody Cidade cidade){
        try {
            return cidadeService.salvar(cidade, ResourceEnum.RESOURCE_POST);
        } catch (EstadoNaoExistenteException e) {
            throw new NegocioException("Estado não cadastrado");
        }
    }

    @ResponseStatus (HttpStatus.OK)
    @PutMapping(value = "/{cidadeId}")
    public Cidade alterarCidade(@PathVariable("cidadeId") Long id
            , @RequestBody Cidade cidade) {
        Cidade cidadeAtual;
        cidadeAtual = cidadeService.buscar(id);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        try {
            return cidadeService.salvar(cidadeAtual, ResourceEnum.RESOURCE_PUT);
        } catch (EstadoNaoExistenteException e) {
            throw new NegocioException("Estado em uso");
        }
    }

    @ResponseStatus (HttpStatus.OK)
    @DeleteMapping(value = "/{cidadeId}")
    public void remover (@PathVariable("cidadeId") Long id){
        cidadeService.deletar(id);
    }

}
