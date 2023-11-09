package com.uqac.cart.presentation;

import com.uqac.cart.business.CartService;
import com.uqac.cart.dto.AddItemToCart;
import com.uqac.cart.dto.CartResponse;
import com.uqac.cart.infra.Cart;
import com.uqac.cart.infra.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCartItems(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCart(userId);
            List<CartItem> cartsItems = cartService.getCartItems(userId);
            CartResponse cartResponse = new CartResponse();
            cartResponse.setLocked(cart.getLocked());
            cartResponse.setItem(cartsItems);
            return ResponseEntity.ok(cartResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCart(userId);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<?> addToCart(@RequestBody AddItemToCart addItemToCart) {
        try {
            cartService.addToCart(addItemToCart.getUserId(), addItemToCart.getProductId(), addItemToCart.getQuantity());
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/lock/{userId}")
    public ResponseEntity<?> lockCart(@PathVariable Long userId) {
        try {
            cartService.lockCart(userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(false);
        }
        return ResponseEntity.ok(true);
    }

    @PutMapping("/unlock/{userId}")
    public ResponseEntity<Boolean> unlockCart(@PathVariable Long userId) {
        try {
            cartService.unlockCart(userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(false);
        }
        return ResponseEntity.ok(true);
    }
}
