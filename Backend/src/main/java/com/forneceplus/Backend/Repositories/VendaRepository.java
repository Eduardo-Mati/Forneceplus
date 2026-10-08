package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Venda;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface VendaRepository extends MongoRepository<Venda, String> {
}
