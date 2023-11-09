package com.uqac.produit.business;

import com.uqac.produit.dto.ProductDTO;
import com.uqac.produit.infra.Product;
import com.uqac.produit.infra.ProductJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJPA productRepository;

    @Autowired
    public ProductServiceImpl(ProductJPA productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product addProduct(ProductDTO product) {
        Product productMap = MapperDTOToInfra.map(product);
        productRepository.save(productMap);
        List<Product> products = productRepository.findProductsByName(productMap.getName());
        return products.get(products.size() - 1);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO product) {
        Product productMap = MapperDTOToInfra.map(product);
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not " +
                "found"));
        if (productMap.getName() != null) {
            productToUpdate.setName(productMap.getName());
        }
        if (productMap.getDescription() != null) {
            productToUpdate.setDescription(productMap.getDescription());
        }
        if (productMap.getPrice() != 0) {
            productToUpdate.setPrice(productMap.getPrice());
        }
        if (productMap.getQuantity() != 0) {
            productToUpdate.setQuantity(productMap.getQuantity());
        }
        productRepository.save(productToUpdate);
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not " +
                "found"));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public Boolean decreaseQuantityProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getQuantity() < quantity) {
            return false;
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return true;
    }

    @Override
    public void increaseQuantityProduct(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    @Override
    public Integer getQuantityProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getQuantity();
    }
}
