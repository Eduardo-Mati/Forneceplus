package com.forneceplus.Backend.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String CPF;
    private String endereco;
    private String telefone;
}
