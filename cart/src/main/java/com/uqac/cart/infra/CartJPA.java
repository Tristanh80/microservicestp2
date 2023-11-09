package com.uqac.cart.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartJPA extends JpaRepository<Cart, Long> {

    List<Cart> findCartsByUserId(Long userId);
}
