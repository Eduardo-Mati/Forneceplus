package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Fornecedor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FornecedorRepository extends MongoRepository<Fornecedor, String> {
}
