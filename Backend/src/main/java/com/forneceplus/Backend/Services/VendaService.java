package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public List<Venda> ListarVendas(){
        return vendaRepository.findAll();
    }

    public Venda SalvarVenda(Venda venda){
        return vendaRepository.save(venda);
    }

    public void DeletarVenda(Long id){
        vendaRepository.deleteById(id);
    }
    public Venda AtualizarVenda(Long id, Venda vendaNova){
        Venda vendaAntiga = vendaRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (vendaNova.getData() != null) {
            vendaAntiga.setData(vendaNova.getData());
        }
        if (vendaNova.getFornecedor() != null){
            vendaAntiga.setFornecedor(vendaNova.getFornecedor());
        }
        if(vendaNova.getObservacao() != null){
            vendaAntiga.setObservacao(vendaNova.getObservacao());
        }
        if (vendaNova.getProduto() != null) {
            vendaAntiga.setProduto(vendaNova.getProduto());
        }
        if(vendaNova.getQuantidade() != null){
            vendaAntiga.setQuantidade(vendaNova.getQuantidade());
        }
        if (vendaNova.getUsuario() != null) {
            vendaAntiga.setUsuario(vendaNova.getUsuario());
        }
        if (vendaNova.getStatus() != null) {
            vendaAntiga.setStatus(vendaNova.getStatus());
        }
        if (vendaNova.getFormaPagamento() != null) {
            vendaAntiga.setFormaPagamento(vendaNova.getFormaPagamento());
        }
        if (vendaNova.getValor() != null) {
            vendaAntiga.setValor(vendaNova.getValor());
        }



        return vendaRepository.save(vendaAntiga);
    }
    public Optional<Venda> BuscarVendaPorId(Long id){
        return vendaRepository.findById(id);
    }
}
