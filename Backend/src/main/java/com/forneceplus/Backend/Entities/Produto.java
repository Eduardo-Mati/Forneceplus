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
public class Produto {

    @Id @GeneratedValue
    private Long idProduto;
    @Column(nullable = false)
    private String nomeProduto;
    @Column(nullable = false)
    private String quantidade;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String fornecedor;
    @Column(nullable = false)
    private String preco;
}
