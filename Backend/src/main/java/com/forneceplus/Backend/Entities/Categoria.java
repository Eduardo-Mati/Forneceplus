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
public class Categoria {

    @Id
    private Long idCategoria;
    @Column(name = "nome_categoria", nullable = false)
    private String nomeCategoria;
    @Column(name = "descricao_categoria", nullable = false)
    private String descricao;
    @Column(name = "status_categoria", nullable = false)
    private String status;
}
