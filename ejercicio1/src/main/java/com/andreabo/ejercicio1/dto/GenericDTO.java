package com.andreabo.ejercicio1.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenericDTO {
    private String name;
    private int stock = -1;
}
