package com.uqac.produit.presentation;

import com.uqac.produit.business.MapperDTOToInfra;
import com.uqac.produit.business.ProductService;
import com.uqac.produit.dto.ProductDTO;
import com.uqac.produit.infra.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTO = MapperDTOToInfra.mapToDTO(productService.getAllProducts());
        if (productDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDTO productDTOMap = MapperDTOToInfra.map(product);
        return ResponseEntity.ok(productDTOMap);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.addProduct(productDTO);
        ProductDTO productDTOMap = MapperDTOToInfra.map(product);
        return ResponseEntity.ok(productDTOMap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = productService.updateProduct(id, productDTO);
        ProductDTO productDTOMap = MapperDTOToInfra.map(product);
        return ResponseEntity.ok(productDTOMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@RequestBody @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/decrease/{quantity}")
    public ResponseEntity<?> decreaseProductQuantity(@PathVariable Long productId, @PathVariable int quantity) {
        Boolean response = productService.decreaseQuantityProduct(productId, quantity);
        if (!response) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}/{quantity}")
    public ResponseEntity<Integer> getQuantityProduct(@PathVariable Long productId, @PathVariable int quantity) {
        return ResponseEntity.ok(productService.getQuantityProduct(productId));
    }

    @PutMapping("/{productId}/increase/{quantity}")
    public ResponseEntity<?> increaseProductQuantity(@PathVariable Long productId, @PathVariable int quantity) {
        productService.increaseQuantityProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

}
