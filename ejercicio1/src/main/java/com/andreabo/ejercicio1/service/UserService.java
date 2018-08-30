package com.andreabo.ejercicio1.service;

import com.andreabo.ejercicio1.dto.GenericDTO;
import com.andreabo.ejercicio1.model.AppUser;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface UserService {
    boolean updateUser(Long id, AppUser appUser);

    List<AppUser> getUsers();

    boolean deleteUser(Long id);

    Long addUser(GenericDTO genericDTO);

    ByteArrayInputStream generateReport();
}
