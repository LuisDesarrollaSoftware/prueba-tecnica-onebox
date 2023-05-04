package com.pruebatecnicaonebox.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto implements Serializable {

    private UUID id;
    private String description;
    private Double amount;


}
