package com.andreabo.ejercicio1.controller;

import com.andreabo.ejercicio1.model.AppUser;
import com.andreabo.ejercicio1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins ="*")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public boolean updateProduct(@PathVariable Long id, @RequestBody AppUser appUser){
        return userService.updateUser(id, appUser);
    }

    @GetMapping
    public List<AppUser> getInventory(){
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> generateReport(){

        ByteArrayInputStream report = userService.generateReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=productsreport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(report));
    }
}
