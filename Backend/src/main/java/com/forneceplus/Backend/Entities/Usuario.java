package com.forneceplus.Backend.Entities;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity // Indica que esta classe representa uma tabela no banco de dados.
@Document(collection = "usuarios")
@Data // Gera getters, setters, toString, equals e hashCode com o Lombok.
@NoArgsConstructor // Gera um construtor sem argumentos, exigido pelo JPA.
@AllArgsConstructor // Gera um construtor com todos os atributos.
public class Usuario {

    @Id // Define a chave primária da tabela.
    //@GeneratedValue // Gera automaticamente o ID do usuário.
    private String idUsuario;

    //@Column(nullable = false) // A coluna não aceita null no banco.
    @NotBlank // Exige nome preenchido.
    private String nome;

    //@Column(nullable = false)
    @NotBlank // Exige e-mail informado.
    @Email // Verifica se o texto possui formato de e-mail válido.
    private String email;

    //@Column(nullable = false)
    @NotBlank
    private String senha;

    //@Column(nullable = false)
    @NotBlank
    @Pattern(regexp = "\\d{11}") // Exige exatamente 11 dígitos para o CPF.
    private String CPF;

    //@Column(nullable = false)
    @NotBlank
    private String endereco;
    
    //@Column(nullable = false)
    @NotNull
    private Long telefone;
}
