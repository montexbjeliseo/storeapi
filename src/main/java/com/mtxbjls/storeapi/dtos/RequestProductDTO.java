package com.mtxbjls.storeapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestProductDTO {
    private String title;
    private String description;
    private Double price;
    private Long category_id;
    private String[] images;
}
