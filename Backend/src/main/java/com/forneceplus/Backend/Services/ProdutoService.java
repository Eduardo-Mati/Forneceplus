package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Categoria;
import com.forneceplus.Backend.Entities.Fornecedor;
import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import com.forneceplus.Backend.Repositories.CategoriaRepository;
import com.forneceplus.Backend.Repositories.FornecedorRepository;
import com.forneceplus.Backend.Repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Produto> ListarProdutos(){
        return produtoRepository.findAll();
    }

    public Produto SalvarProduto(Produto produto){
        Categoria categoria = categoriaRepository.findById(produto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        produto.setCategoria(categoria);

        Fornecedor fornecedor = fornecedorRepository.findById(produto.getFornecedor().getIdFornecedor())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));
        produto.setFornecedor(fornecedor);

        return produtoRepository.save(produto);
    }

    public Optional<Produto> BuscarProdutoPorId(Long id){
        return produtoRepository.findById(id);
    }

    public Produto AtualizarProduto(Long id, Produto produtoNovo){

        Produto ProdutoAntigo = produtoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produtoNovo.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(produtoNovo.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            ProdutoAntigo.setCategoria(categoria);
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
            Fornecedor fornecedor = fornecedorRepository.findById(produtoNovo.getFornecedor().getIdFornecedor())
                    .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado"));
            ProdutoAntigo.setFornecedor(fornecedor);
        }


        return produtoRepository.save(ProdutoAntigo);
    }

    public void DeletarProduto(Long id){
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}
