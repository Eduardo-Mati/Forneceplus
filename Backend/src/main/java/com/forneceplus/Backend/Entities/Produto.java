package com.forneceplus.Backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class Produto {

    @Id // Define a chave primária da tabela.
    @GeneratedValue // Gera automaticamente o ID do produto.
    private Long idProduto;

    @Column(nullable = false) // A coluna não aceita null no banco.
    @NotBlank // Exige nome preenchido.
    private String nomeProduto;

    @Column(nullable = false)
    @NotBlank
    private String quantidade;

    @Column(nullable = false)
    @NotBlank
    private String descricao;

    @ManyToOne // Muitos produtos podem pertencer a uma categoria.
    @JoinColumn(name = "idCategoria", nullable = false) // Cria a FK da categoria.
    @NotNull // Exige uma categoria associada.
    private Categoria categoria;

    @ManyToOne // Muitos produtos podem pertencer a um fornecedor.
    @JoinColumn(name = "idFornecedor", nullable = false) // Cria a FK do fornecedor.
    @NotNull // Exige um fornecedor associado.
    private Fornecedor fornecedor;

    @Column(nullable = false)
    @NotBlank
    private String preco;
}
