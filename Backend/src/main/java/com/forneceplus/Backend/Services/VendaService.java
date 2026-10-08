package com.forneceplus.Backend.Services;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import com.forneceplus.Backend.Entities.Usuario;
import com.forneceplus.Backend.Repositories.UsuarioRepository;
import com.forneceplus.Backend.Repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Venda> ListarVendas(){
        return vendaRepository.findAll();
    }

    public Venda SalvarVenda(Venda venda){
        Usuario usuario = usuarioRepository.findById(venda.getUsuario().getIdUsuario())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        venda.setUsuario(usuario);
        return vendaRepository.save(venda);
    }

    public void DeletarVenda(String id){
        if (!vendaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venda não encontrada");
        }
        vendaRepository.deleteById(id);
    }
    public Venda AtualizarVenda(String id, Venda vendaNova){
        Venda vendaAntiga = vendaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));


        vendaAntiga.setData(vendaNova.getData());


        vendaAntiga.setObservacao(vendaNova.getObservacao());

        if (vendaNova.getItens() != null) {
            vendaAntiga.setItens(vendaNova.getItens());
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
        if (vendaNova.getUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(vendaNova.getUsuario().getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            vendaAntiga.setUsuario(usuario);
        }



        return vendaRepository.save(vendaAntiga);
    }
    public Optional<Venda> BuscarVendaPorId(String id){
        return vendaRepository.findById(id);
    }
}
