package com.uqac.commande.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {
    private Long userId;

    private Long productId;

    private int quantity;
}
