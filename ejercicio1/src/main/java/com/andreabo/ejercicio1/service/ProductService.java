package com.andreabo.ejercicio1.service;

import com.andreabo.ejercicio1.dto.GenericDTO;
import com.andreabo.ejercicio1.model.Product;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductService {
    boolean updateProduct(Long id, Product product);

    List<Product> getInventory();

    boolean deleteProduct(Long id);

    Long addProduct(GenericDTO genericDTO);

    ByteArrayInputStream generateReport();
}
