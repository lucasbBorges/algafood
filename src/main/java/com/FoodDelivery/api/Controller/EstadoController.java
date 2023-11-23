package com.FoodDelivery.api.Controller;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.model.Estado;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoService estadoService;

    @GetMapping
    public List<Estado> listarEstados(){
        return estadoService.listar();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{estadoId}")
    public Estado buscarEstado(@PathVariable("estadoId") Long id){
        return estadoService.buscar(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Estado cadastrarEstado(@Valid @RequestBody Estado estado){
        return estadoService.salvar(estado, ResourceEnum.RESOURCE_POST);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{estadoId}")
    public Estado alterarEstado(@PathVariable("estadoId") Long id
            , @RequestBody Estado estado) {
        Estado estadoAtual;
        estadoAtual = estadoService.buscar(id);
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return estadoService.salvar(estadoAtual, ResourceEnum.RESOURCE_PUT);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{estadoId}")
    public void remover (@PathVariable("estadoId") Long id){
        estadoService.deletar(id);
    }

}
