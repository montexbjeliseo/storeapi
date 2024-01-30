package com.mtxbjls.storeapi.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestProductDTO {
    private String title;
    private String description;
    private Double price;
    private Long category_id;
    private String[] images;
}
