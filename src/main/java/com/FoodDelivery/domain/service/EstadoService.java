package com.FoodDelivery.domain.service;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.exceptions.estado.EstadoEmUsoException;
import com.FoodDelivery.domain.exceptions.estado.EstadoExistenteException;
import com.FoodDelivery.domain.exceptions.estado.EstadoNaoExistenteException;
import com.FoodDelivery.domain.model.Cozinha;
import com.FoodDelivery.domain.model.Estado;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {


    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar(){
        return estadoRepository.findAll();
    }

    public List<Estado> buscarPorNome(String nome) {
        return estadoRepository.findByNome(nome);
    }

    public Estado salvar(Estado estado, ResourceEnum ENUM){
        if (!estadoRepository.findByNome(estado.getNome()).isEmpty()
                && ENUM == ResourceEnum.RESOURCE_POST){
            throw new EstadoExistenteException();
        }
        return estadoRepository.save(estado);
    }
    public Estado buscar(Long id){
        return estadoRepository.findById(id)
                .orElseThrow(EstadoNaoExistenteException::new);
    }

    public void deletar(Long id){
        try{
            Estado estado = buscar(id);
            estadoRepository.delete(estado);
        }catch (DataIntegrityViolationException e){
            throw new EstadoEmUsoException();
        }
    }
}
