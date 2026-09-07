package com.forneceplus.Backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class Venda{

    @Id // Define a chave primária da tabela.
    @GeneratedValue // Gera automaticamente o ID da venda.
    private Long idVenda;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL) // Uma venda possui vários itens; as operações são propagadas aos itens.
    private List<ItemVenda> itens;

    @Column(nullable = false) // A coluna não aceita null no banco.
    @NotNull // Exige valor informado.
    @Positive // Exige valor maior que zero.
    private BigDecimal valor;

    @Column(nullable = false)
    @NotBlank
    private String data;

    @ManyToOne // Muitas vendas podem pertencer ao mesmo usuário.
    @JoinColumn(name = "idUsuario", nullable = false) // Cria a FK do usuário.
    @NotNull // Exige um usuário associado.
    private Usuario usuario;

    @Column(nullable = false)
    @NotBlank
    private String status;

    @Column(nullable = false)
    @NotNull
    private String observacao;

    @Column(nullable = false)
    @NotBlank
    private String formaPagamento;
}

// cascade: define se ações (salvar, deletar) na entidade "pai" devem se propagar automaticamente pras entidades "filhas" relacionadas.
// Ex: CascadeType.ALL na Venda -> deletar a Venda deleta os ItemVenda dela.