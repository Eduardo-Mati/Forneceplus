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
public class Fornecedor {

    @Id @GeneratedValue
    private Long idFornecedor;
    @Column(nullable = false)
    private String nomeFornecedor;
    @Column(nullable = false)
    private String emailFornecedor;
    @Column(nullable = false)
    private String descricaoFornecedor;
    @Column(length = 50, nullable = false)
    private Long telefoneFornecedor;
    @Column(nullable = false)
    private String enderecoFornecedor;
    @Column(length = 14, nullable = false)
    private String CNPJFornecedor;



}
