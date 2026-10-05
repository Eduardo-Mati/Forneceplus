package com.forneceplus.Backend.Services;

import com.forneceplus.Backend.Entities.Fornecedor;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import com.forneceplus.Backend.Repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> ListarFornecedores(){
        return fornecedorRepository.findAll();
    }
    public Fornecedor SalvarFornecedor(Fornecedor fornecedor){
        return fornecedorRepository.save(fornecedor);
    }
    public Optional<Fornecedor> BuscarFornecedorPorId(String id){
        return fornecedorRepository.findById(id);
    }
    public void DeletarFornecedor(String id){
        if (!fornecedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fornecedor não encontrado");
        }
        fornecedorRepository.deleteById(id);
    }
    public Fornecedor AtualizarFornecedor(String id, Fornecedor fornecedorNovo){
        Fornecedor fornecedorAntigo = fornecedorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));


        fornecedorAntigo.setDescricaoFornecedor(fornecedorNovo.getDescricaoFornecedor());

        if (fornecedorNovo.getCNPJFornecedor() != null){
            fornecedorAntigo.setCNPJFornecedor(fornecedorNovo.getCNPJFornecedor());
        }

        fornecedorAntigo.setEmailFornecedor(fornecedorNovo.getEmailFornecedor());


        fornecedorAntigo.setEnderecoFornecedor(fornecedorNovo.getEnderecoFornecedor());

        if (fornecedorNovo.getNomeFornecedor() != null) {
            fornecedorAntigo.setNomeFornecedor(fornecedorNovo.getNomeFornecedor());
        }

        fornecedorAntigo.setTelefoneFornecedor(fornecedorNovo.getTelefoneFornecedor());


        return fornecedorRepository.save(fornecedorAntigo);
    }









}
