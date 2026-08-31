package com.forneceplus.Backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda{

    @Id @GeneratedValue
    private Long idVenda;
    @Column(nullable = false)
    private String quantidade;
    @Column(nullable = false)
    private String produto;
    @Column(nullable = false)
    private String valor;
    @Column(nullable = false)
    private String data;
    @Column(nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String fornecedor;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String observacao;
    @Column(nullable = false)
    private String formaPagamento;
}
