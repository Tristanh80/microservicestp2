package com.uqac.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCart {

    private Long userId;

    private Long productId;

    private int quantity;
}
