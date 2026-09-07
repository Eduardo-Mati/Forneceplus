package com.forneceplus.Backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class Fornecedor {

    @Id // Define a chave primária da tabela.
    @GeneratedValue // Gera automaticamente o valor do ID.
    private Long idFornecedor;

    @Column(nullable = false) // A coluna não aceita valor nulo no banco.
    @NotBlank // Exige texto preenchido e diferente de espaços.
    private String nomeFornecedor;

    @Column(nullable = false)
    @NotBlank
    private String emailFornecedor;

    @Column(nullable = false)
    @NotBlank
    private String descricaoFornecedor;

    @Column(length = 50, nullable = false) // Define tamanho máximo e proíbe null no banco.
    @NotNull // Exige que o valor seja informado.
    private Long telefoneFornecedor;

    @Column(nullable = false)
    @NotBlank
    private String enderecoFornecedor;
    
    @Column(length = 14, nullable = false) // Armazena até 14 caracteres e não aceita null.
    @NotBlank // Exige que o CNPJ não esteja vazio.
    @Pattern(regexp = "\\d{14}") // Exige exatamente 14 dígitos numéricos.
    private String CNPJFornecedor;



}
