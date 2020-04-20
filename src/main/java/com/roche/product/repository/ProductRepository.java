package com.roche.product.repository;

import com.roche.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Override
    @Query("select e from #{#entityName} e where e.deleteFlag=false")
    public List<Product> findAll();

    @Override
    @Query("select e from #{#entityName} e where e.deleteFlag=false and e.id=?1")
    public Optional<Product> findById(String id);

    @Query("select e from #{#entityName} e where e.deleteFlag=true")
    public List<Product> findAllDeleted();

    @Query("select e from #{#entityName} e where e.deleteFlag=true and e.id=?1")
    public Optional<Product> findDeleted(String id);

    @Query("update #{#entityName} e set e.deleteFlag=true where e.id=?1")
    @Transactional
    @Modifying
    public void softDelete(String id);
}
