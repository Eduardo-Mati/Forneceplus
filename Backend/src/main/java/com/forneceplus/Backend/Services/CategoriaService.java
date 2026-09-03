package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Categoria;
import com.forneceplus.Backend.Repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> ListarCategorias(){
        return categoriaRepository.findAll();
    }
    public Categoria SalvarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }
    public Optional<Categoria> BuscarCategoriaPorId(Long id){
        return categoriaRepository.findById(id);
    }
    public void DeletarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }
    public Categoria AtualizarCategoria(Long id, Categoria categoriaNova){

        Categoria categoriaAntiga = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (categoriaNova.getDescricao() != null){
            categoriaAntiga.setDescricao(categoriaNova.getDescricao());
        }
        if(categoriaNova.getNomeCategoria() != null){
            categoriaAntiga.setNomeCategoria(categoriaNova.getNomeCategoria());
        }
        if (categoriaNova.getStatus() != null) {
            categoriaAntiga.setStatus(categoriaNova.getStatus());
        }

        return categoriaRepository.save(categoriaAntiga);
    }
}
