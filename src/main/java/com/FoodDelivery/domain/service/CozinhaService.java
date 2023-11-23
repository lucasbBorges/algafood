package com.FoodDelivery.domain.service;

import com.FoodDelivery.domain.exceptions.cozinha.CozinhaEmUsoException;
import com.FoodDelivery.domain.exceptions.cozinha.CozinhaExistenteException;
import com.FoodDelivery.domain.exceptions.cozinha.CozinhaNaoExistenteException;
import com.FoodDelivery.domain.model.Cozinha;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.model.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {
    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    public List<Cozinha> buscarPorNome(String nome) {
        return cozinhaRepository.findByNome(nome);
    }

    public Cozinha salvar(Cozinha cozinha, ResourceEnum ENUM){
        if (!cozinhaRepository.findByNome(cozinha.getNome()).isEmpty()
                && ENUM == ResourceEnum.RESOURCE_POST){
            throw new CozinhaExistenteException();
        }
        return cozinhaRepository.save(cozinha);
    }
    public Cozinha buscar(Long id){
        return cozinhaRepository.findById(id)
                .orElseThrow(CozinhaNaoExistenteException::new);
    }

    public void deletar(Long id){
        try{
            Cozinha cozinha = buscar(id);
            cozinhaRepository.delete(cozinha);
        }catch (DataIntegrityViolationException e){
            throw new CozinhaEmUsoException();
        }
    }
}
