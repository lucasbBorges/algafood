package com.FoodDelivery.domain.service;

import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import com.FoodDelivery.domain.exceptions.cidade.CidadeEmUsoException;
import com.FoodDelivery.domain.exceptions.cidade.CidadeExistenteException;
import com.FoodDelivery.domain.exceptions.cidade.CidadeNaoExistenteException;
import com.FoodDelivery.domain.model.Cidade;
import com.FoodDelivery.domain.model.Estado;
import com.FoodDelivery.domain.model.ResourceEnum;
import com.FoodDelivery.domain.model.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {
    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoService estadoService;

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    public List<Cidade> buscarPorNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    public Cidade salvar(Cidade cidade, ResourceEnum ENUM){
        if (!cidadeRepository.findByNome(cidade.getNome()).isEmpty()
                && ENUM == ResourceEnum.RESOURCE_POST){
            throw new CidadeExistenteException();
        }
        Estado estado = estadoService.buscar(cidade.getEstado().getId());
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }
    public Cidade buscar(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(CidadeNaoExistenteException::new);
    }

    public void deletar(Long id){
        try{
            Cidade cidade = buscar(id);
            cidadeRepository.delete(cidade);
        }catch (DataIntegrityViolationException e){
            throw new CidadeEmUsoException();
        }
    }
}
