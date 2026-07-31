package com.handmadecrafts.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDto {
    private Integer imageId;
    private Integer productId;
    private String imageUrl;
}
