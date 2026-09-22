package com.forneceplus.Backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class ItemVenda {

    @Id // Define a chave primária da tabela.
    @GeneratedValue // Gera automaticamente o ID do item.
    private Long idItem;

    @ManyToOne // Muitos itens podem pertencer a uma mesma venda.
    @JoinColumn(name = "idVenda", nullable = false) // Cria a FK para a venda e exige que ela seja informada.
    @NotNull // Exige uma venda associada.
    @JsonIgnoreProperties(value = "itens", allowSetters = true) // Oculta apenas a lista de itens ao responder, mas aceita venda no JSON recebido.
    private Venda venda;

    @ManyToOne // Muitos itens podem referenciar um mesmo produto.
    @JoinColumn(name = "idProduto", nullable = false) // Cria a FK para o produto.
    @NotNull // Exige um produto associado.
    private Produto produto;

    @Column(nullable = false) // A coluna não aceita null no banco.
    @NotNull // Exige que a quantidade seja informada.
    @Positive // Exige quantidade maior que zero.
    private Integer quantidade;

    @Column(nullable = false)
    @NotNull // Exige que o preço seja informado.
    @Positive // Exige preço maior que zero.
    private BigDecimal preco;

    @Column(nullable = false)
    @NotBlank // Exige que o status esteja preenchido.
    private String statusItem;

}

// @OneToMany: "um-pra-muitos". Lado inverso, só de leitura, não cria coluna própria, usa mappedBy pra apontar o campo dono no outro lado.
// @ManyToMany: "muitos-pra-muitos". Cria uma tabela extra no meio (tabela de junção) pra guardar os pares de IDs relacionados dos dois lados.