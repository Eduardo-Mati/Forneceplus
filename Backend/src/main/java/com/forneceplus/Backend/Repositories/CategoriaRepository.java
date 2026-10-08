package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {

    //List<Categoria> findByIdCategoria(String Id);

    /*
        @Query(" { 'status': { '$eq': ?0} }")
        List<Categoria> findByStatus(String status);

        Essas são duas formas diferentes de resolver uma consulta específica no repository.

        O máximo de questão de FILTRO, LISTA E OUTROS TIPOS DE MAPEAMENTO recomendado colocar no repository
    */

}
