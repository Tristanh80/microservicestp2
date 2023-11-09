package com.uqac.cart.business;

import com.uqac.cart.infra.Cart;
import com.uqac.cart.infra.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> addToCart(Long userId, Long productId, int quantity);
    List<CartItem> getCartItems(Long userId);

    Cart getCart(Long userId);

    void clearCart(Long userId);

    Boolean lockCart(Long userId);

    Boolean unlockCart(Long userId);
}
