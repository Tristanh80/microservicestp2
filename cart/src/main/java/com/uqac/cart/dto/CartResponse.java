package com.uqac.cart.dto;

import com.uqac.cart.infra.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Boolean locked;

    private List<CartItem> item;
}
