package com.forneceplus.Backend.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda{
    private Long idVenda;
    private String quantidade;
    private String produto;
    private String valor;
    private String data;
    private String usuario;
    private String fornecedor;
    private String status;
    private String observacao;
    private String formaPagamento;
}
