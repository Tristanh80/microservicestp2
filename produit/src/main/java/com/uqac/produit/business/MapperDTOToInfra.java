package com.uqac.produit.business;

import com.uqac.produit.dto.ProductDTO;
import com.uqac.produit.infra.Product;

import java.util.List;
import java.util.stream.Collectors;

public class MapperDTOToInfra {

    private MapperDTOToInfra() {
        throw new IllegalStateException("Utility class");
    }

    public static Product map(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }

    public List<Product> mapToProduct(List<ProductDTO> productsDTO) {
        return productsDTO.stream().map(MapperDTOToInfra::map).collect(Collectors.toList());
    }

    public static ProductDTO map(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        return productDTO;
    }

    public static List<ProductDTO> mapToDTO(List<Product> products) {
        return products.stream().map(MapperDTOToInfra::map).collect(Collectors.toList());
    }

}
