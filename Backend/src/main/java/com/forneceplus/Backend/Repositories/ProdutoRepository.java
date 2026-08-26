package com.forneceplus.Backend.Repositories;

import com.forneceplus.Backend.Entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
