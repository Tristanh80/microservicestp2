package com.uqac.commande.business;

import com.uqac.commande.dto.CartItems;
import com.uqac.commande.dto.CartResponse;
import com.uqac.commande.dto.CreateCommandeDTO;
import com.uqac.commande.dto.Product;
import com.uqac.commande.infra.Commande;
import com.uqac.commande.infra.CommandeJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CommandeServiceImpl implements CommandeService{


    private RestTemplate restTemplate;

    private CommandeJPA commandeRepository;

    @Autowired
    public CommandeServiceImpl(RestTemplate restTemplate, CommandeJPA commandeRepository) {
        this.restTemplate = restTemplate;
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Boolean createCommande(CreateCommandeDTO createCommandeDTO) {
        // Récupérez les informations de la commande à partir du DTO
        Long userId = createCommandeDTO.getUserId();
        Long cartId = createCommandeDTO.getCartId();
        Commande commande;

        // Vérifiez si le panier existe
        String cartServiceUrl = "http://cart:8081/cart/{cartId}";
        ResponseEntity<CartResponse> response = restTemplate.getForEntity(cartServiceUrl, CartResponse.class,
                cartId);

        if (response.getBody() != null && !response.getBody().getLocked()) {

            String lockUrl = "http://cart:8081/cart/lock/{userId}";
            try {
                restTemplate.put(lockUrl, null, userId);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            // Créez la commande
            commande = new Commande();
            commande.setUserId(userId);
            commande.setCartId(cartId);
            commande.setPickupDate(createCommandeDTO.getPickupDate());
            commande.setEmail(createCommandeDTO.getEmail());

            // Enregistrez la commande dans la base de données
        } else {
            return false;
        }
        BigDecimal total = BigDecimal.ZERO;
        String productServiceUrl = "http://produit:8080/products/{productId}";
        for (CartItems cartItem : response.getBody().getItem()) {
            ResponseEntity<Product> product = restTemplate.getForEntity(productServiceUrl, Product.class,
                    cartItem.getProductId());
            int quantity = Objects.requireNonNull(product.getBody()).getQuantity();
            if (quantity < cartItem.getQuantity()) {
                return false;
            }
            BigDecimal productPrice = BigDecimal.valueOf(product.getBody().getPrice());
            BigDecimal quantityDecimal = BigDecimal.valueOf(cartItem.getQuantity());
            if (product.getBody() != null) {
                total = total.add(productPrice.multiply(quantityDecimal));
            }

        }
        commande.setTotal(total.doubleValue());

        String decreaseQuantityUrl = "http://produit:8080/products/{productId}/decrease/{quantity}";
        for (CartItems cartItem : response.getBody().getItem()) {
            try {
                restTemplate.put(decreaseQuantityUrl, null, cartItem.getProductId(), cartItem.getQuantity());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        commandeRepository.save(commande);
        return true;
    }

    @Override
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @Override
    public List<Commande> getCommandesByUserId(Long userId) {
        return commandeRepository.findCommandesByUserId(userId);
    }

    @Override
    public void cancelCommande(Long userId, Long cartId) {
        commandeRepository.deleteCommandeByUserIdAndCartId(userId, cartId);
    }
}
