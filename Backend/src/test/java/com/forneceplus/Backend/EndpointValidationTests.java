package com.forneceplus.Backend;

import com.forneceplus.Backend.Controllers.ItemVendaController;
import com.forneceplus.Backend.Controllers.VendaController;
import com.forneceplus.Backend.Entities.ItemVenda;
import com.forneceplus.Backend.Entities.Venda;
import com.forneceplus.Backend.Services.ItemVendaService;
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
    private ItemVendaService itemVendaService;
    @InjectMocks
    private VendaController vendaController;
    @InjectMocks
    private ItemVendaController itemVendaController;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendaController, itemVendaController).build();
    }

    @Test
    void endpointsDeVendaRecebemBodyEIdCorretamente() throws Exception {
        Venda venda = new Venda(1L, "2", "Produto", "20", "2026-09-03", "Usuario", "Fornecedor", "ABERTA", "", "PIX");
        when(vendaService.SalvarVenda(any(Venda.class))).thenReturn(venda);
        when(vendaService.AtualizarVenda(eq(1L), any(Venda.class))).thenReturn(venda);
        when(vendaService.BuscarVendaPorId(1L)).thenReturn(Optional.of(venda));

        mockMvc.perform(post("/vendas")
                        .contentType("application/json")
                        .content("{\"quantidade\":\"2\",\"produto\":\"Produto\",\"valor\":\"20\",\"data\":\"2026-09-03\",\"usuario\":\"Usuario\",\"fornecedor\":\"Fornecedor\",\"status\":\"ABERTA\",\"observacao\":\"\",\"formaPagamento\":\"PIX\"}"))
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
        ItemVenda item = new ItemVenda(1L, "Produto", "2", "10", "2026-09-03", "ABERTO");
        when(itemVendaService.BuscarItemPorId(1L)).thenReturn(Optional.of(item));
        when(itemVendaService.BuscarItemPorId(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/itens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idItem").value(1))
                .andExpect(jsonPath("$.produto").value("Produto"));
        mockMvc.perform(get("/itens/2"))
                .andExpect(status().isNotFound());
    }
}
