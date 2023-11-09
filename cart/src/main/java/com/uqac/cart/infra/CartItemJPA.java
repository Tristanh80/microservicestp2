package com.uqac.cart.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemJPA extends JpaRepository<CartItem, Long> {
}
