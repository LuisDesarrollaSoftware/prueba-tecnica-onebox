package com.pruebatecnicaonebox.model.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto implements Serializable{
    private UUID id;
    private List<ProductDto> productList;
    private Timestamp lastUpdate;
}
