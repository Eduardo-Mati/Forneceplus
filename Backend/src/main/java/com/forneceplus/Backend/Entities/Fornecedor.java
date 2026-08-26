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
public class Fornecedor {

    @Id
    private Long idFornecedor;
    @Column(name = "nome_fornecedor", nullable = false)
    private String nomeFornecedor;
    @Column(name = "email_fornecedor", nullable = false)
    private String emailFornecedor;
    @Column(name = "descriçãoFornecedor", nullable = false)
    private String descricaoFornecedor;
    @Column(name= "telefoneFornecedor", length = 50, nullable = false)
    private int telefoneFornecedor;
    @Column(name = "enderecoFornecedor", nullable = false)
    private String enderecoFornecedor;
    @Column(name = "CNPJFornecedor", length = 14, nullable = false)
    private String CNPJFornecedor;



}
