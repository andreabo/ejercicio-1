package com.andreabo.ejercicio1.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminDTO {
    private String name;
    private String password;
}
