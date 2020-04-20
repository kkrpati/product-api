package com.roche.product.repository;

import com.roche.product.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void addProduct() {
        //Given
        Product originalProduct = new Product();
        originalProduct.setName("Product1");
        originalProduct.setPrice(new BigDecimal("12.45"));
        Product savedProduct = entityManager.persist(originalProduct);
        entityManager.flush();
        //When Product Found
        Product foundProduct = productRepository.findById(savedProduct.getId()).get();
        //Then Assert
        assertThat(foundProduct.getName()).isEqualTo(originalProduct.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(originalProduct.getPrice());
        assertThat(savedProduct.getCreationDate()).isEqualTo(originalProduct.getCreationDate());

    }

    @Test
    public void updateProduct() {
        //Given
        Product originalProduct = new Product();
        originalProduct.setName("Product2");
        originalProduct.setPrice(new BigDecimal("12.45"));
        Product savedProduct = entityManager.persist(originalProduct);
        entityManager.flush();
        savedProduct.setPrice((new BigDecimal("133.78"))); //New Price
        Product updatedProduct = productRepository.save(savedProduct);

        //When Product retrieved
        Product foundProduct = productRepository.findById(updatedProduct.getId()).get();
        //Then Assert
        assertThat(updatedProduct.getPrice()).isEqualTo(foundProduct.getPrice());
        assertThat(savedProduct.getPrice()).isEqualTo(foundProduct.getPrice());
    }

    @Test
    public void findAllProducts() {
        //Given
        Product product1 = new Product();
        product1.setName("Product11");
        product1.setPrice(new BigDecimal("12.45"));
        Product savedProduct1 = entityManager.persist(product1);
        entityManager.flush();
        Product product2 = new Product();
        product2.setName("Product22");
        product2.setPrice(new BigDecimal("11.23"));
        Product savedProduct2 = entityManager.persist(product2);
        entityManager.flush();
        //When Product Found
        List<Product> products = productRepository.findAll();
        //Then Assert
        //TODO : To refactor later
        assertThat(products.size()).isEqualTo(2);
        if (products.get(0).getId().equals(savedProduct1.getId())) {
            assertThat(true);
        } else {
            assertThat(products.get(1).getId().equals(savedProduct1.getId()));
        }

        if (products.get(1).getId().equals(savedProduct2.getId())) {
            assertThat(true);
        } else {
            assertThat(products.get(0).getId().equals(savedProduct2.getId()));
        }
    }

    @Test
    public void softDeleteProduct() {
        //Given
        Product product = new Product();
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Product savedProduct = entityManager.persist(product);
        entityManager.flush();
        productRepository.softDelete(savedProduct.getId());

        //When fetched using findById
        Optional<Product> optional = productRepository.findById(savedProduct.getId());
        //Then
        assertThat(!optional.isPresent());

        //Fetch Soft Deleted
        Optional<Product> optionalDeleted = productRepository.findDeleted(savedProduct.getId());
        assertThat(!optional.isPresent());
        assertThat(optionalDeleted.get().getId()).isEqualTo(savedProduct.getId());
    }

    @Test
    public void findAllSoftDeleted() {
        //Given
        Product product = new Product();
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Product savedProduct = entityManager.persist(product);
        entityManager.flush();


        Product product1 = new Product();
        product.setName("Product22");
        product.setPrice(new BigDecimal("22.67"));
        Product savedProduct1 = entityManager.persist(product1);
        entityManager.flush();
        productRepository.softDelete(savedProduct.getId());
        productRepository.softDelete(savedProduct1.getId());
        //When
        List<Product> deletedProducts = productRepository.findAllDeleted();
        List<Product> products = productRepository.findAll();
        //Then
        assertThat(products.size()).isEqualTo(0);
        assertThat(deletedProducts.size()).isEqualTo(2);
    }
}

