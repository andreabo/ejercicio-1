package com.andreabo.ejercicio1.service;

import com.andreabo.ejercicio1.dto.AdminDTO;
import com.andreabo.ejercicio1.dto.GenericDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public List<AdminDTO> login(String name, String password) {
        List<AdminDTO> response = new ArrayList<>();
        if(adminName.equals(name)&&adminPassword.equals(password)){
            response.add(new AdminDTO().setName(name).setPassword(password));
        }
        return response;
    }

    @Override
    public Long addUserOrProduct(GenericDTO genericDTO) {
        return genericDTO.getStock()>=0
                ?productService.addProduct(genericDTO)
                :userService.addUser(genericDTO);
    }
}
