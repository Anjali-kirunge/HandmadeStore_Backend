package com.handmadecrafts.backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private CategoryDto category;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
