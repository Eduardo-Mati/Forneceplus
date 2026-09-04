package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.Fornecedor;
import com.forneceplus.Backend.Services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    private ResponseEntity<List<Fornecedor>> ListarFornecedores(){
        return ResponseEntity.ok(fornecedorService.ListarFornecedores());
    }

    @PostMapping
    private ResponseEntity<Fornecedor> SalvarFornecedor(@RequestBody Fornecedor fornecedor){

        Fornecedor fornecedorSalvo = fornecedorService.SalvarFornecedor(fornecedor);

        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Fornecedor> BuscarFornecedorPorId(@PathVariable Long id){

        Optional<Fornecedor> fornecedor = fornecedorService.BuscarFornecedorPorId(id);

        if(fornecedor.isPresent()){
            return ResponseEntity.ok(fornecedor.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarFornecedor(@PathVariable Long id){
        fornecedorService.DeletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Fornecedor> AtualizarFornecedor(@PathVariable Long id,@RequestBody Fornecedor fornecedor){
        Optional<Fornecedor> fornecedorAntigo = fornecedorService.BuscarFornecedorPorId(id);

        if(!fornecedorAntigo.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            Fornecedor fornecedorAtualizado = fornecedorService.AtualizarFornecedor(id, fornecedor);
            return ResponseEntity.status(HttpStatus.OK).body(fornecedorAtualizado);
        }
    }

}
