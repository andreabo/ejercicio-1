package com.andreabo.ejercicio1.controller;

import com.andreabo.ejercicio1.dto.AdminDTO;
import com.andreabo.ejercicio1.dto.GenericDTO;
import com.andreabo.ejercicio1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="*")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admins")
    public List<AdminDTO> login(@RequestParam String name, @RequestParam String password){
        return adminService.login(name, password);
    }

    @PostMapping
    public Long addUserOrProduct(@RequestBody GenericDTO genericDTO){
        return adminService.addUserOrProduct(genericDTO);
    }
}
