package com.mtxbjls.storeapi.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestCategoryDTO {
    private String name;
    private String image;
}
