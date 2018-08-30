package com.andreabo.ejercicio1.controller;

import com.andreabo.ejercicio1.model.Product;
import com.andreabo.ejercicio1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins ="*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable Long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @GetMapping
    public List<Product> getInventory(){
        return productService.getInventory();
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> generateReport(){

        ByteArrayInputStream report = productService.generateReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=productsreport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(report));
    }
}
