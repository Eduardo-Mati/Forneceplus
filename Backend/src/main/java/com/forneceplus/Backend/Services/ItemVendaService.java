package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import com.forneceplus.Backend.Repositories.ItemVendaRepository;
import com.forneceplus.Backend.Repositories.ProdutoRepository;
import com.forneceplus.Backend.Repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    public List<ItemVenda> ListarItens(){
        return itemVendaRepository.findAll();
    }

    public ItemVenda SalvarItem(ItemVenda item){
        Produto produto = produtoRepository.findById(item.getProduto().getIdProduto())
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        Venda venda = vendaRepository.findById(item.getVenda().getIdVenda())
            .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));
        item.setProduto(produto);
        item.setVenda(venda);
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
            Produto produto = produtoRepository.findById(dadosNovos.getProduto().getIdProduto())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            itemAntigo.setProduto(produto);
        }
        if (dadosNovos.getVenda() != null) {
            Venda venda = vendaRepository.findById(dadosNovos.getVenda().getIdVenda())
                    .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));
            itemAntigo.setVenda(venda);
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
        if (!itemVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item de venda não encontrado");
        }
        itemVendaRepository.deleteById(id);
    }
}
