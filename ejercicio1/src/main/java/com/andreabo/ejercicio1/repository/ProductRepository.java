package com.andreabo.ejercicio1.repository;

import com.andreabo.ejercicio1.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAll();
}
