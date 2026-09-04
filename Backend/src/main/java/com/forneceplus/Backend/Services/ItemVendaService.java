package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import com.forneceplus.Backend.Repositories.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public List<ItemVenda> ListarItens(){
        return itemVendaRepository.findAll();
    }

    public ItemVenda SalvarItem(ItemVenda item){
        return itemVendaRepository.save(item);
    }

    public ItemVenda AtualizarItem(Long id, ItemVenda dadosNovos){
        ItemVenda itemAntigo = itemVendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Item de venda não encontrado"));


        if (dadosNovos.getPreco() != null){
            itemAntigo.setPreco(dadosNovos.getPreco());
        }
        if(dadosNovos.getQuantidade() != null){
            itemAntigo.setQuantidade(dadosNovos.getQuantidade());
        }
        if (dadosNovos.getProduto() != null){
            itemAntigo.setProduto(dadosNovos.getProduto());
        }
        if (dadosNovos.getStatusItem() != null) {
            itemAntigo.setStatusItem(dadosNovos.getStatusItem());
        }



        return itemVendaRepository.save(itemAntigo);
    }

    public Optional<ItemVenda> BuscarItemPorId(Long id){
        return itemVendaRepository.findById(id);
    }

    public void DeletarItem(Long id){
        itemVendaRepository.deleteById(id);
    }
}
