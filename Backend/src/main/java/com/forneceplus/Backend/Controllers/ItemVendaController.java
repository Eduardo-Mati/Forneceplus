package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Services.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @GetMapping
    private ResponseEntity<List<ItemVenda>> ListarItens(){
        return ResponseEntity.ok(itemVendaService.ListarItens());
    }

    @PostMapping
    private ResponseEntity<ItemVenda> AdicionarItem(@RequestBody ItemVenda item){
        ItemVenda itemSalvo = itemVendaService.SalvarItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalvo);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ItemVenda> BuscarItemPorId(@PathVariable Long id){

        Optional<ItemVenda> item = itemVendaService.BuscarItemPorId(id);

        if(item.isPresent()){
            return ResponseEntity.ok(item.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<ItemVenda> AtualizarItem(@PathVariable Long id,@RequestBody ItemVenda dadosParciais){
        ItemVenda itemAtualizado = itemVendaService.AtualizarItem(id, dadosParciais);
        return ResponseEntity.status(HttpStatus.OK).body(itemAtualizado);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarItem(@PathVariable Long id){
        itemVendaService.DeletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
