package com.mtxbjls.storeapi.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseCategoryDTO {
    private Long id;
    private String name;
    private String image;
}
