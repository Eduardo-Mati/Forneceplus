package com.forneceplus.Backend.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private Long idProduto;
    private String nomeProduto;
    private String descricao;
    private String categoria;
    private String fornecedor;
    private String preco;
}
