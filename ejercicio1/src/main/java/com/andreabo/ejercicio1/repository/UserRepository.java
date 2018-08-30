package com.andreabo.ejercicio1.repository;

import com.andreabo.ejercicio1.model.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<AppUser, Long> {
    List<AppUser> findAll();
}
