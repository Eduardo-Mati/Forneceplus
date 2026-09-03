package com.forneceplus.Backend;

import com.forneceplus.Backend.Entities.Categoria;
import com.forneceplus.Backend.Entities.Fornecedor;
import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Entities.Usuario;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Repositories.CategoriaRepository;
import com.forneceplus.Backend.Repositories.FornecedorRepository;
import com.forneceplus.Backend.Repositories.ItemVendaRepository;
import com.forneceplus.Backend.Repositories.ProdutoRepository;
import com.forneceplus.Backend.Repositories.UsuarioRepository;
import com.forneceplus.Backend.Repositories.VendaRepository;
import com.forneceplus.Backend.Services.CategoriaService;
import com.forneceplus.Backend.Services.FornecedorService;
import com.forneceplus.Backend.Services.ItemVendaService;
import com.forneceplus.Backend.Services.ProdutoService;
import com.forneceplus.Backend.Services.UsuarioService;
import com.forneceplus.Backend.Services.VendaService;
import com.forneceplus.Backend.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceValidationTests {

    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private FornecedorRepository fornecedorRepository;
    @Mock
    private ItemVendaRepository itemVendaRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private CategoriaService categoriaService;
    @InjectMocks
    private FornecedorService fornecedorService;
    @InjectMocks
    private ItemVendaService itemVendaService;
    @InjectMocks
    private ProdutoService produtoService;
    @InjectMocks
    private UsuarioService usuarioService;
    @InjectMocks
    private VendaService vendaService;

    @Test
    void atualizacaoParcialMantemCamposNaoInformados() {
        ItemVenda item = new ItemVenda(1L, "Produto antigo", "2", "10", "2026-09-03", "ABERTO");
        ItemVenda dadosNovos = new ItemVenda(null, null, null, "12", null, null);
        when(itemVendaRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemVendaRepository.save(any(ItemVenda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemVenda atualizado = itemVendaService.AtualizarItem(1L, dadosNovos);

        assertEquals("Produto antigo", atualizado.getProduto());
        assertEquals("2", atualizado.getQuantidade());
        assertEquals("12", atualizado.getPreco());
        assertEquals("2026-09-03", atualizado.getDataVenda());
        assertEquals("ABERTO", atualizado.getStatusVenda());
        verify(itemVendaRepository).save(item);
    }

    @Test
    void atualizacoesComIdInexistenteLancamExcecao404() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        when(fornecedorRepository.findById(1L)).thenReturn(Optional.empty());
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        when(itemVendaRepository.findById(1L)).thenReturn(Optional.empty());
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.AtualizarCategoria(1L, new Categoria()));
        assertThrows(ResourceNotFoundException.class, () -> fornecedorService.AtualizarFornecedor(1L, new Fornecedor()));
        assertThrows(ResourceNotFoundException.class, () -> produtoService.AtualizarProduto(1L, new Produto()));
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.AtualizarUsuario(1L, new Usuario()));
        assertThrows(ResourceNotFoundException.class, () -> itemVendaService.AtualizarItem(1L, new ItemVenda()));
        assertThrows(ResourceNotFoundException.class, () -> vendaService.AtualizarVenda(1L, new Venda()));
    }
}
