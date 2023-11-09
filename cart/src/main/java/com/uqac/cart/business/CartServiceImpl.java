package com.uqac.cart.business;

import com.uqac.cart.dto.Product;
import com.uqac.cart.infra.Cart;
import com.uqac.cart.infra.CartItem;
import com.uqac.cart.infra.CartItemJPA;
import com.uqac.cart.infra.CartJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private RestTemplate restTemplate;

    private CartItemJPA cartItemJPA;

    private CartJPA cartJPA;

    @Autowired
    public CartServiceImpl(CartItemJPA cartItemJPA, CartJPA cartJPA, RestTemplate restTemplate) {
        this.cartItemJPA = cartItemJPA;
        this.cartJPA = cartJPA;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CartItem> addToCart(Long userId, Long productId, int quantity) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        Cart cart;
        if (carts.size() == 0) {
            cart = new Cart();
            cart.setUserId(userId);
        } else {
            cart = carts.get(carts.size() - 1);
            if (cart.getLocked()) {
                throw new RuntimeException("Cart is locked");
            }
        }
        String productServiceUrl = "http://produit:8080/products/{productId}";

        try {
            ResponseEntity<Product> product = restTemplate.getForEntity(productServiceUrl, Product.class, productId);
            if (product.getBody() == null) {
                throw new RuntimeException("Product not found");
            }
            if (product.getBody().getQuantity() < quantity) {
                throw new RuntimeException("Not enough product in stock");
            }
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        List<CartItem> cartItems = cart.getItems();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(cartItem);
        cart.setItems(cartItems);
        cart.setLocked(false);
        cartItemJPA.save(cartItem);
        cartJPA.save(cart);
        return cartItems;
    }

    @Override
    public List<CartItem> getCartItems(Long userId) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        if (carts.size() == 0) {
            throw new RuntimeException("Cart not found");
        }
        return carts.get(carts.size() - 1).getItems();
    }

    @Override
    public Cart getCart(Long userId) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        if (carts.size() == 0) {
            throw new RuntimeException("Cart not found");
        }
        return carts.get(carts.size() - 1);
    }

    @Override
    public void clearCart(Long userId) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        if (carts.size() == 0) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = carts.get(carts.size() - 1);
        if (cart.getLocked()) {
            throw new RuntimeException("Cart is locked");
        }
        cart.setItems(null);
        cartJPA.save(cart);
    }

    @Override
    public Boolean lockCart(Long userId) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        if (carts.size() == 0) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = carts.get(carts.size() - 1);
        cart.setLocked(true);
        cartJPA.save(cart);
        return true;
    }

    @Override
    public Boolean unlockCart(Long userId) {
        List<Cart> carts = cartJPA.findCartsByUserId(userId);
        if (carts.size() == 0) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = carts.get(carts.size() - 1);
        cart.setLocked(false);
        cartJPA.save(cart);
        return true;
    }
}
