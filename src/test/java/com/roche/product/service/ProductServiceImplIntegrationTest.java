package com.roche.product.service;

import com.roche.product.model.Product;
import com.roche.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ProductServiceImplIntegrationTest {

    @TestConfiguration
    static class ProductServiceTestContextConfiguration {

        @Bean
        public ProductService productService() {
            return new ProductService();
        }
    }

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

/*    @Before
    public void setUp() {
        Product product = new Product();
        product.setId("12323");
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Mockito.when(productRepository.findById("12323"))
                .thenReturn(java.util.Optional.of(product));
    }*/

    @Test
    public void testGetProduct() {
        Product product = new Product();
        product.setId("12323");
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Mockito.when(productRepository.findById("12323"))
                .thenReturn(java.util.Optional.of(product));

        Product productRetrieved = productService.getProduct("12323");
        assertThat(product.getId()).isEqualTo(productRetrieved.getId());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductWhenProductNotPresent() {
        Mockito.when(productRepository.findById("12323"))
                .thenReturn(java.util.Optional.empty());

        Product productRetrieved = productService.getProduct("12323");
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setId("12323");
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Mockito.when(productRepository.findById("12323"))
                .thenReturn(java.util.Optional.of(product));
        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        Product productSaved = productService.updateProduct(product, "12323");

        assertThat(productSaved.getId()).isEqualTo(product.getId());
        assertThat(productSaved.getName()).isEqualTo(product.getName());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateProductNotPresent() {
        Product product = new Product();
        product.setId("12323");
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));
        Mockito.when(productRepository.findById("12323"))
                .thenReturn(java.util.Optional.of(product));
        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        Product product2 = new Product();
        product2.setId("23455");
        product2.setName("Product22");
        product2.setPrice(new BigDecimal("124.09"));
        Product productSaved = productService.updateProduct(product, "23455");
    }

    @Test
    public void testFindAll() {
        Product product = new Product();
        product.setId("12323");
        product.setName("Product11");
        product.setPrice(new BigDecimal("12.45"));

        Product product2 = new Product();
        product2.setId("234234");
        product2.setName("Product22");
        product2.setPrice(new BigDecimal("11.09"));

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> productsRetrieved = productService.getAllProducts();
        assertThat(productsRetrieved.size()).isEqualTo(2);

        assertThat(productsRetrieved.size()).isEqualTo(2);
        if (productsRetrieved.get(0).getId().equals(product.getId())) {
            assertThat(true);
        } else {
            assertThat(productsRetrieved.get(1).getId().equals(product.getId()));
        }

        if (productsRetrieved.get(1).getId().equals(product2.getId())) {
            assertThat(true);
        } else {
            assertThat(productsRetrieved.get(0).getId().equals(product2.getId()));
        }

    }

}
