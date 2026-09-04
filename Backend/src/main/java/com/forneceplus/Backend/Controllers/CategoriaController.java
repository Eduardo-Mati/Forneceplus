package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.Categoria;
import com.forneceplus.Backend.Services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    private ResponseEntity<List<Categoria>> ListarCategorias(){
        return ResponseEntity.ok(categoriaService.ListarCategorias());
    }

    @PostMapping
    private ResponseEntity<Categoria> SalvarCategoria(@RequestBody Categoria categoria){
        //return ResponseEntity.ok(categoriaService.SalvarCategoria(categoria));

        Categoria categoriaNova = categoriaService.SalvarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNova);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Categoria> BuscarCategoriaPorId(@PathVariable Long id){

        Optional<Categoria> categoria = categoriaService.BuscarCategoriaPorId(id);

        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarCategoria(@PathVariable Long id){
        categoriaService.DeletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Categoria> AtualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria){
        Optional<Categoria> categoriaAntiga = categoriaService.BuscarCategoriaPorId(id);

        if(!categoriaAntiga.isPresent()){
            return ResponseEntity.notFound().build();
        }else {
            Categoria categoriaAtualizada = categoriaService.AtualizarCategoria(id, categoria);
            return ResponseEntity.status(HttpStatus.OK).body(categoriaAtualizada);
        }
    }

}
