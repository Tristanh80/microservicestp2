package com.uqac.produit.business;

import com.uqac.produit.dto.ProductDTO;
import com.uqac.produit.infra.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product addProduct(ProductDTO product);
    Product updateProduct(Long id, ProductDTO product);
    void deleteProduct(Long id);

    Boolean decreaseQuantityProduct(Long productId, int quantity);

    void increaseQuantityProduct(Long productId, int quantity);

    Integer getQuantityProduct(Long productId);
}
