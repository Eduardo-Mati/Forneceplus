package com.forneceplus.Backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    @Id
    private Long idItem;
    @Column(name = "ProdutoVenda", unique = true, nullable = false)
    private String produto;
    @Column(name = "quantidadeVenda", unique = true, nullable = false)
    private String quantidade;
    @Column(name = "precoVenda", nullable = false)
    private String preco;
    @Column(name = "dataVenda", nullable = false)
    private String dataVenda;
    @Column(name = "statusVenda", nullable = false)
    private String statusVenda;

}
