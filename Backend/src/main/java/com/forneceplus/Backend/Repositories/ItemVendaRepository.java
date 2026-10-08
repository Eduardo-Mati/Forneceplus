package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.ItemVenda;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemVendaRepository extends MongoRepository<ItemVenda, String> {
}
