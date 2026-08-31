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
public class Categoria {

    @Id @GeneratedValue
    private Long idCategoria;
    @Column(nullable = false)
    private String nomeCategoria;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String status;
}
