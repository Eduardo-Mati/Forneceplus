package com.forneceplus.Backend.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class Categoria {

    @Id // Define a chave primária da tabela.
    @GeneratedValue // Gera automaticamente o valor do ID.
    private Long idCategoria;

    @Column(nullable = false) // Cria uma coluna que não aceita valor nulo no banco.
    @NotBlank // Exige texto preenchido, diferente de null, vazio ou apenas espaços.
    private String nomeCategoria;

    @Column(nullable = false)
    @NotBlank
    private String descricao;
    
    @Column(nullable = false)
    @NotBlank
    private String status;
}
