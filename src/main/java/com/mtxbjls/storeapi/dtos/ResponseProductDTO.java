package com.mtxbjls.storeapi.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseProductDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private ResponseCategoryDTO category;
    private String[] images;
}
