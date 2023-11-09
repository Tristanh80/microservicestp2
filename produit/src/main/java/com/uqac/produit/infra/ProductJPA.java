package com.uqac.produit.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJPA extends JpaRepository<Product, Long> {

    List<Product> findProductsByName(String name);

}
