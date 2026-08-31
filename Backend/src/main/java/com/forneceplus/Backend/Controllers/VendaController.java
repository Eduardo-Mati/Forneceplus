package com.forneceplus.Backend.Controllers;

import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Repositories.VendaRepository;
import com.forneceplus.Backend.Services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    public VendaService vendaService;

    @GetMapping
    private ResponseEntity<List<Venda>> ListarVendas(){
        return ResponseEntity.ok(vendaService.ListarVendas());
    }

    @PostMapping
    private ResponseEntity<Venda> SalvarVenda(Venda venda) {
        Venda vendaSalva = vendaService.SalvarVenda(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
    }

    @PutMapping
    private ResponseEntity<Venda> AtualizarVenda(Long id, Venda venda) {
        Venda vendaAtualizada = vendaService.AtualizarVenda(id, venda);
        return ResponseEntity.status(HttpStatus.OK).body(vendaAtualizada);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> DeletarVenda(Long id) {
        vendaService.DeletarVenda(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Venda> BuscarVendaPorId(Long id) {
        Optional<Venda> venda = vendaService.BuscarVendaPorId(id);

        if(venda.isPresent()) {
            return ResponseEntity.ok(venda.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
