package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProdutoRepository extends MongoRepository<Produto, String> {
}
