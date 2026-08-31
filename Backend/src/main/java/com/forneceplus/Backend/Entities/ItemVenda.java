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
public class ItemVenda {

    @Id @GeneratedValue
    private Long idItem;
    @Column(unique = true, nullable = false)
    private String produto;
    @Column(unique = true, nullable = false)
    private String quantidade;
    @Column(nullable = false)
    private String preco;
    @Column(nullable = false)
    private String dataVenda;
    @Column(nullable = false)
    private String statusVenda;

}
