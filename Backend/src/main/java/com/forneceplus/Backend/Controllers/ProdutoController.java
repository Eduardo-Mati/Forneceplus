package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    private ResponseEntity<List<Produto>> ListarProdutos(){
        return ResponseEntity.ok(produtoService.ListarProdutos());
    }

    @PostMapping
    private ResponseEntity<Produto> SalvarProduto(@RequestBody Produto produto){
        Produto produtoSalvo = produtoService.SalvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Produto> BuscarProdutoPorId (@PathVariable Long id){

        Optional<Produto> produto = produtoService.BuscarProdutoPorId(id);

        if(produto.isPresent()){
            return ResponseEntity.ok(produto.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<Produto> AtualizarProduto(@PathVariable Long id, @RequestBody Produto produto){
        Produto produtoAtualizado = produtoService.AtualizarProduto(id, produto);
        return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarProduto(@PathVariable Long id){
        produtoService.DeletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
