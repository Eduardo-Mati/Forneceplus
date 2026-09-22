package com.forneceplus.Backend;

import com.forneceplus.Backend.Controllers.ItemVendaController;
import com.forneceplus.Backend.Controllers.CategoriaController;
import com.forneceplus.Backend.Controllers.FornecedorController;
import com.forneceplus.Backend.Controllers.ProdutoController;
import com.forneceplus.Backend.Controllers.UsuarioController;
import com.forneceplus.Backend.Controllers.VendaController;
import com.forneceplus.Backend.Entities.Categoria;
import com.forneceplus.Backend.Entities.Fornecedor;
import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Entities.Produto;
import com.forneceplus.Backend.Entities.Usuario;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Services.CategoriaService;
import com.forneceplus.Backend.Services.FornecedorService;
import com.forneceplus.Backend.Services.ItemVendaService;
import com.forneceplus.Backend.Services.ProdutoService;
import com.forneceplus.Backend.Services.UsuarioService;
import com.forneceplus.Backend.Services.VendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EndpointValidationTests {

    private MockMvc mockMvc;

    @Mock
    private VendaService vendaService;
        @Mock
        private CategoriaService categoriaService;
        @Mock
        private FornecedorService fornecedorService;
        @Mock
        private ProdutoService produtoService;
        @Mock
        private UsuarioService usuarioService;
    @Mock
    private ItemVendaService itemVendaService;
    @InjectMocks
    private VendaController vendaController;
    @InjectMocks
    private ItemVendaController itemVendaController;
        @InjectMocks
        private CategoriaController categoriaController;
        @InjectMocks
        private FornecedorController fornecedorController;
        @InjectMocks
        private ProdutoController produtoController;
        @InjectMocks
        private UsuarioController usuarioController;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                categoriaController,
                fornecedorController,
                produtoController,
                usuarioController,
                itemVendaController,
                vendaController).build();
    }

    @Test
    void endpointsDeVendaRecebemBodyEIdCorretamente() throws Exception {
                Venda venda = mock(Venda.class);
        when(venda.getIdVenda()).thenReturn(1L);
        when(vendaService.SalvarVenda(any(Venda.class))).thenReturn(venda);
        when(vendaService.AtualizarVenda(eq(1L), any(Venda.class))).thenReturn(venda);
                when(vendaService.BuscarVendaPorId(1L)).thenReturn(Optional.of(venda));

        mockMvc.perform(post("/vendas")
                        .contentType("application/json")
                        .content("{\"valor\":\"20\",\"data\":\"2026-09-03\",\"usuario\":{\"idUsuario\":1},\"status\":\"ABERTA\",\"observacao\":\"\",\"formaPagamento\":\"PIX\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(put("/vendas/1")
                        .contentType("application/json")
                        .content("{\"status\":\"FECHADA\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/vendas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenda").value(1));
        mockMvc.perform(delete("/vendas/1"))
                .andExpect(status().isNoContent());

        verify(vendaService).AtualizarVenda(eq(1L), any(Venda.class));
        verify(vendaService).DeletarVenda(1L);
    }

    @Test
    void getDeItemRetornaEntidadeE404QuandoNaoExiste() throws Exception {
        ItemVenda item = mock(ItemVenda.class);
        when(item.getIdItem()).thenReturn(1L);
        when(item.getProduto()).thenReturn(mock(Produto.class));
        when(itemVendaService.BuscarItemPorId(1L)).thenReturn(Optional.of(item));
        when(itemVendaService.BuscarItemPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/itens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.produto").exists());
        mockMvc.perform(get("/itens/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void endpointsDeCadastroRecebemJsonERetornam201() throws Exception {
        Categoria categoria = mock(Categoria.class);
        Fornecedor fornecedor = mock(Fornecedor.class);
        Produto produto = mock(Produto.class);
        Usuario usuario = mock(Usuario.class);

        when(categoriaService.SalvarCategoria(any(Categoria.class))).thenReturn(categoria);
        when(fornecedorService.SalvarFornecedor(any(Fornecedor.class))).thenReturn(fornecedor);
        when(produtoService.SalvarProduto(any(Produto.class))).thenReturn(produto);
        when(usuarioService.SalvarUsuario(any(Usuario.class))).thenReturn(usuario);
        when(usuario.getCPF()).thenReturn("52998224725");

        mockMvc.perform(post("/categorias")
                        .contentType("application/json")
                        .content("{\"nomeCategoria\":\"Eletronicos\",\"descricao\":\"Produtos\",\"status\":\"ATIVA\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/fornecedores")
                        .contentType("application/json")
                        .content("{\"nomeFornecedor\":\"Fornecedor\",\"emailFornecedor\":\"contato@empresa.com\",\"descricaoFornecedor\":\"Distribuidor\",\"telefoneFornecedor\":11999999999,\"enderecoFornecedor\":\"Rua A\",\"CNPJFornecedor\":\"12345678000195\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content("{\"nomeProduto\":\"Notebook\",\"quantidade\":\"10\",\"descricao\":\"Computador\",\"categoria\":{\"idCategoria\":1},\"fornecedor\":{\"idFornecedor\":1},\"preco\":\"3500.00\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/usuarios")
                        .contentType("application/json")
                        .content("{\"nome\":\"Maria\",\"email\":\"maria@email.com\",\"senha\":\"segredo\",\"CPF\":\"52998224725\",\"endereco\":\"Rua B\",\"telefone\":11999999999}"))
                .andExpect(status().isCreated());
    }

    @Test
    void cpfInvalidoRetorna400() throws Exception {
        mockMvc.perform(post("/usuarios")
                        .contentType("application/json")
                        .content("{\"nome\":\"Maria\",\"email\":\"maria@email.com\",\"senha\":\"segredo\",\"CPF\":\"11111111111\",\"endereco\":\"Rua B\",\"telefone\":11999999999}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void buscasPorIdInexistenteRetornam404EmTodosOsRecursos() throws Exception {
        when(categoriaService.BuscarCategoriaPorId(99L)).thenReturn(Optional.empty());
        when(fornecedorService.BuscarFornecedorPorId(99L)).thenReturn(Optional.empty());
        when(produtoService.BuscarProdutoPorId(99L)).thenReturn(Optional.empty());
        when(usuarioService.BuscarUsuarioPorId(99L)).thenReturn(Optional.empty());
        when(itemVendaService.BuscarItemPorId(99L)).thenReturn(Optional.empty());
        when(vendaService.BuscarVendaPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/categorias/99")).andExpect(status().isNotFound());
        mockMvc.perform(get("/fornecedores/99")).andExpect(status().isNotFound());
        mockMvc.perform(get("/produtos/99")).andExpect(status().isNotFound());
        mockMvc.perform(get("/usuarios/99")).andExpect(status().isNotFound());
        mockMvc.perform(get("/itens/99")).andExpect(status().isNotFound());
        mockMvc.perform(get("/vendas/99")).andExpect(status().isNotFound());
    }

    @Test
    void putEDeleteFuncionamParaTodosOsRecursos() throws Exception {
        Categoria categoria = mock(Categoria.class);
        Fornecedor fornecedor = mock(Fornecedor.class);
        Produto produto = mock(Produto.class);
        Usuario usuario = mock(Usuario.class);
        ItemVenda item = mock(ItemVenda.class);
        when(categoriaService.BuscarCategoriaPorId(1L)).thenReturn(Optional.of(categoria));
        when(fornecedorService.BuscarFornecedorPorId(1L)).thenReturn(Optional.of(fornecedor));
        when(produtoService.BuscarProdutoPorId(1L)).thenReturn(Optional.of(produto));
        when(usuarioService.BuscarUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(itemVendaService.BuscarItemPorId(1L)).thenReturn(Optional.of(item));
        when(categoriaService.AtualizarCategoria(eq(1L), any(Categoria.class))).thenReturn(categoria);
        when(fornecedorService.AtualizarFornecedor(eq(1L), any(Fornecedor.class))).thenReturn(fornecedor);
        when(produtoService.AtualizarProduto(eq(1L), any(Produto.class))).thenReturn(produto);
        when(usuarioService.AtualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);
        when(itemVendaService.AtualizarItem(eq(1L), any(ItemVenda.class))).thenReturn(item);

        mockMvc.perform(put("/categorias/1").contentType("application/json")
                        .content("{\"nomeCategoria\":\"Atualizada\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/fornecedores/1").contentType("application/json")
                        .content("{\"nomeFornecedor\":\"Atualizado\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/produtos/1").contentType("application/json")
                        .content("{\"preco\":\"99.90\",\"categoria\":{\"idCategoria\":1},\"fornecedor\":{\"idFornecedor\":1}}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/usuarios/1").contentType("application/json")
                        .content("{\"nome\":\"Atualizado\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/itens/1").contentType("application/json")
                        .content("{\"quantidade\":3,\"preco\":\"20.00\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/categorias/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/fornecedores/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/produtos/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/usuarios/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/itens/1")).andExpect(status().isNoContent());
        mockMvc.perform(delete("/vendas/1")).andExpect(status().isNoContent());
    }
}
