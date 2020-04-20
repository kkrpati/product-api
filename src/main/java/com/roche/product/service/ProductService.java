package com.roche.product.service;

import com.roche.product.model.Product;
import com.roche.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(String id) {
        LOG.info("Getting the product with given id:" + id);
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }else {
            throw new IllegalArgumentException();
        }
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product addProduct(Product product) {
            LOG.info("Saving product :");
            return productRepository.save(product);
    }

    public Product updateProduct(Product productToUpdate, String id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product foundProduct = optional.get();
            foundProduct.setName(productToUpdate.getName());
            foundProduct.setPrice(productToUpdate.getPrice());
            return productRepository.save(foundProduct);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProduct(String id) {
        productRepository.softDelete(id);
    }
}
