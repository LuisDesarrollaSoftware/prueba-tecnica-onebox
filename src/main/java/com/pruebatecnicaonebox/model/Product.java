package com.pruebatecnicaonebox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @Generated(value = GenerationTime.ALWAYS)
    private UUID id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double amount;

    @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Cart> cartList;
}
