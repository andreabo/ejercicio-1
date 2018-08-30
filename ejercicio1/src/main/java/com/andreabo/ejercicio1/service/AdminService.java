package com.andreabo.ejercicio1.service;


import com.andreabo.ejercicio1.dto.AdminDTO;
import com.andreabo.ejercicio1.dto.GenericDTO;

import java.util.List;

public interface AdminService {
    List<AdminDTO> login(String name, String password);

    Long addUserOrProduct(GenericDTO genericDTO);
}
