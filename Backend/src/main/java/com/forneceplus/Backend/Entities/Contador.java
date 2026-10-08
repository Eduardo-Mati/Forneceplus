package com.forneceplus.Backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Guarda o último ID numérico usado em cada coleção (ex.: { _id: "categorias", sequencia: 3 }).
@Document(collection = "contadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contador {

    @Id // Nome da coleção à qual o contador pertence.
    private String id;

    private long sequencia;
}
