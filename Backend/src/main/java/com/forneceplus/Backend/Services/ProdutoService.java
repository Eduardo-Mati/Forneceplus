package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> ListarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto SalvarProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Optional<Produto> BuscarProdutoPorId(Long id){
        return produtoRepository.findById(id);
    }

    public Produto AtualizarProduto(Long id, Produto produtoNovo){

        Produto ProdutoAntigo = produtoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (produtoNovo.getCategoria() != null) {
            ProdutoAntigo.setCategoria(produtoNovo.getCategoria());
        }
        if (produtoNovo.getPreco() != null){
            ProdutoAntigo.setPreco(produtoNovo.getPreco());
        }
        if(produtoNovo.getQuantidade() != null){
            ProdutoAntigo.setQuantidade(produtoNovo.getQuantidade());
        }
        if (produtoNovo.getDescricao() != null){
            ProdutoAntigo.setDescricao(produtoNovo.getDescricao());
        }
        if (produtoNovo.getNomeProduto() != null) {
            ProdutoAntigo.setNomeProduto(produtoNovo.getNomeProduto());
        }
        if(produtoNovo.getFornecedor() != null){
            ProdutoAntigo.setFornecedor(produtoNovo.getFornecedor());
        }


        return produtoRepository.save(ProdutoAntigo);
    }

    public void DeletarProduto(Long id){
        try {
            produtoRepository.deleteById(id);

        } catch (Exception e) {
            System.out.println("não tem nenhum produto com esse id");
        }
    }
}
